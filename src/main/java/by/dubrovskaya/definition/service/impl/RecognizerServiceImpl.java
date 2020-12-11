package by.dubrovskaya.definition.service.impl;

import by.dubrovskaya.definition.exception.ServiceException;
import by.dubrovskaya.definition.model.Document;
import by.dubrovskaya.definition.model.enumeration.Language;
import by.dubrovskaya.definition.model.result.AlphabeticMethodResult;
import by.dubrovskaya.definition.model.result.NgramMethodResult;
import by.dubrovskaya.definition.repository.DocumentRepository;
import by.dubrovskaya.definition.service.HtmlParser;
import by.dubrovskaya.definition.service.JsonComponent;
import by.dubrovskaya.definition.service.RecognizerService;
import by.dubrovskaya.definition.service.WordService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static by.dubrovskaya.definition.model.enumeration.Language.ENGLISH;
import static by.dubrovskaya.definition.model.enumeration.Language.RUSSIAN;

@Service
@RequiredArgsConstructor
public class RecognizerServiceImpl implements RecognizerService {

    private static final Integer N_GRAM_SIZE = 3;
    private static final Integer MAX_N_GRAM_SIZE = 500;

    private final HtmlParser htmlParser;
    private final JsonComponent jsonComponent;
    private final WordService wordService;
    private final DocumentRepository documentRepository;

    @SneakyThrows
    @Override
    @Transactional(readOnly = true)
    public NgramMethodResult recognizeByNgramMethod(MultipartFile file) {
        String dirtyText = htmlParser.parse(new String(file.getBytes()));
        Map<String, Integer> targetTextNgramFrequencyMap = wordService.buildNgramFrequencyMap(dirtyText);
        List<Document> documents = documentRepository.findAll();
        List<NgramMethodResult> results = new ArrayList<>();
        for (Document doc : documents) {
            int docRank = 0;
            Map<String, Integer> gramWeights = jsonComponent.parseMap(doc.getCompiledNgrams(), Integer.class);
            for (Map.Entry<String, Integer> entry : targetTextNgramFrequencyMap.entrySet()) {
                String gram = entry.getKey();
                Integer gramWeight = entry.getValue();
                Optional<Integer> docGramRank = Optional.ofNullable(gramWeights.get(gram));
                docRank += Math.abs(docGramRank.orElse(MAX_N_GRAM_SIZE) - gramWeight);
            }
            results.add(createRecognizeResult(docRank, dirtyText, doc));
        }
        return results.stream()
                .min(Comparator.comparingInt(NgramMethodResult::getRank))
                .orElseThrow(ServiceException::new);
    }

    @SneakyThrows
    @Override
    public AlphabeticMethodResult recognizeByAlphabetMethod(MultipartFile file) {
        String dirtyText = htmlParser.parse(new String(file.getBytes()));
        Double englishSymbols = 0d;
        Double russianSymbols = 0d;
        for (Character symbol : dirtyText.toCharArray()) {
            if (ENGLISH.getAlphabet().contains(symbol.toString().toUpperCase())) {
                englishSymbols++;
            }

            if (RUSSIAN.getAlphabet().contains(symbol.toString().toUpperCase())) {
                russianSymbols++;
            }
        }

        Integer textTotalSize = dirtyText.length();
        Double englishAlphabetFrequencyRatio = englishSymbols / textTotalSize;
        Double russianAlphabetFrequencyRatio = russianSymbols / textTotalSize;
        Pair<Language, Double> languageToFrequencyRation = englishAlphabetFrequencyRatio >= russianAlphabetFrequencyRatio
                ? Pair.of(ENGLISH, englishAlphabetFrequencyRatio)
                : Pair.of(RUSSIAN, russianAlphabetFrequencyRatio);
        return AlphabeticMethodResult.of(languageToFrequencyRation);
    }

    private NgramMethodResult createRecognizeResult(Integer rank, String testedDoc, Document foundedDoc) {
        return NgramMethodResult.builder()
                .rank(rank)
                .testedDocument(testedDoc)
                .foundedDocument(foundedDoc)
                .language(foundedDoc.getLanguage())
                .build();
    }


}
