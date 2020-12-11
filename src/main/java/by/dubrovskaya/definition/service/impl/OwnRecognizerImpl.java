package by.dubrovskaya.definition.service.impl;

import by.dubrovskaya.definition.model.enumeration.Language;
import by.dubrovskaya.definition.model.result.OwnMethodResult;
import by.dubrovskaya.definition.service.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static by.dubrovskaya.definition.model.enumeration.Language.RUSSIAN;

@Service
@RequiredArgsConstructor
public class OwnRecognizerImpl implements OwnRecognizer {
    private final HtmlParser htmlParser;
    private final FileService fileService;
    private final WordService wordService;

    private static final String ENGLISH_PATH = System.getProperty("user.dir")
            + "/src/main/resources/language/english.html";
    private static final String RUSSIAN_PATH = System.getProperty("user.dir")
            + "/src/main/resources/language/russian.html";


    @SneakyThrows
    @Override
    public OwnMethodResult recognize(MultipartFile file) {
        Map<String, Integer> englishWords = getBaseWords(ENGLISH_PATH);
        Map<String, Integer> russianWords = getBaseWords(RUSSIAN_PATH);
        double englishTotal = totalWordsInMap(englishWords);
        double russianTotal = totalWordsInMap(russianWords);

        String dirtyText = htmlParser.parse(new String(file.getBytes()));
        List<String> words = wordService.receiveClearedDistinctWords(dirtyText);

        Set<String> englishWordsInText = new HashSet<>();
        Set<String> russianWordsInText = new HashSet<>();

        for (String word : words) {
            if (englishWords.containsKey(word)) {
                englishWordsInText.add(word);
            }
            if (russianWords.containsKey(word)) {
                russianWordsInText.add(word);
            }
        }

        double englishFrequency = englishWordsInText.size() / englishTotal;
        double russianFrequency = russianWordsInText.size() / russianTotal;

        OwnMethodResult recognitionData = new OwnMethodResult();
        recognitionData.setLanguage(englishFrequency > russianFrequency ? Language.ENGLISH : RUSSIAN);
        recognitionData.setEnglishWordsFrequency(englishFrequency);
        recognitionData.setRussianWordsFrequency(russianFrequency);

        return recognitionData;
    }


    private Map<String, Integer> getBaseWords(String filePath) {
        String text = htmlParser.parse(fileService.getFromFile(filePath));
        List<String> words = wordService.receiveClearedDistinctWords(text);
        Map<String, Integer> wordsMap = new HashMap<>();
        for (String word : words) {
            if (wordsMap.containsKey(word)) {
                wordsMap.put(word, wordsMap.get(word) + 1);
            } else {
                wordsMap.put(word, 1);
            }
        }
        return wordsMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private double totalWordsInMap(Map<String, Integer> map) {
        double res = 0;
        for (int number : map.values()) {
            res += number;
        }
        return res;
    }
}
