package by.dubrovskaya.definition.service.impl;

import by.dubrovskaya.definition.exception.ServiceException;
import by.dubrovskaya.definition.model.Document;
import by.dubrovskaya.definition.model.result.NgramMethodRecognitionResult;
import by.dubrovskaya.definition.repository.DocumentRepository;
import by.dubrovskaya.definition.service.HtmlParser;
import by.dubrovskaya.definition.service.JsonComponent;
import by.dubrovskaya.definition.service.RecognizerService;
import by.dubrovskaya.definition.service.WordService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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
    public NgramMethodRecognitionResult recognizeByNgramMethod(MultipartFile file) {
        String dirtyText = htmlParser.parse(new String(file.getBytes()));
        Map<String, Integer> targetTextNgramFrequencyMap = wordService.buildNgramFrequencyMap(dirtyText);
        List<Document> documents = documentRepository.findAll();
        List<NgramMethodRecognitionResult> results = new ArrayList<>();
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
                .min(Comparator.comparingInt(NgramMethodRecognitionResult::getRank))
                .orElseThrow(ServiceException::new);
    }

    private NgramMethodRecognitionResult createRecognizeResult(Integer rank, String testedDoc, Document foundedDoc) {
        return NgramMethodRecognitionResult.builder()
                .rank(rank)
                .testedDocument(testedDoc)
                .foundedDocument(foundedDoc)
                .language(foundedDoc.getLanguage())
                .build();
    }
}
