package by.dubrovskaya.definition.service.impl;

import by.dubrovskaya.definition.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final StringService stringService;
    private static final Integer N_GRAM_SIZE = 3;
    private static final Integer MAX_N_GRAM_SIZE = 500;

    @Override
    public Map<String, Integer> buildNgramFrequencyMap(String text) {
        List<String> distinctFilteredWords = receiveClearedDistinctWords(text);

        Map<String, Integer> nGramFrequencyMap = new HashMap<>();
        for (String word : distinctFilteredWords) {
            if (word.length() > N_GRAM_SIZE) {
                int firstSymbol = 0;
                int lastSymbol = N_GRAM_SIZE;
                while (lastSymbol <= word.length()) {
                    String gram = word.substring(firstSymbol, lastSymbol);
                    int gramWeight = gramWeight(distinctFilteredWords, gram);
                    nGramFrequencyMap.put(gram, gramWeight);
                    firstSymbol++;
                    lastSymbol++;
                }
            } else {
                int gramWeight = gramWeight(distinctFilteredWords, word);
                nGramFrequencyMap.put(word, gramWeight);
            }
        }
        return buildNgramLimitedAndSortedFrequencyMap(nGramFrequencyMap, MAX_N_GRAM_SIZE);
    }

    @Override
    public Map<String, Integer> buildNgramLimitedAndSortedFrequencyMap(Map<String, Integer> nGramStructure, Integer maxSize) {
        List<Map.Entry<String, Integer>> sortedAndLimitedGramStructure = nGramStructure.entrySet().stream()
                .sorted((firstEntry, secondEntry) -> Integer.compare(secondEntry.getValue(), firstEntry.getValue()))
                .limit(maxSize)
                .collect(Collectors.toList());

        Map<String, Integer> result = new LinkedHashMap<>();
        int index = sortedAndLimitedGramStructure.size();
        for (Map.Entry<String, Integer> entry : sortedAndLimitedGramStructure) {
            result.put(entry.getKey(), index);
            index--;
        }
        return result;
    }

    @Override
    public List<String> receiveClearedDistinctWords(String dirtyText) {
        String clearedText = stringService.clearTextFromAuxiliarySymbols(dirtyText);
        return stringService.createStreamOfDistinctWords(clearedText)
                .filter(word -> word.length() >= N_GRAM_SIZE)
                .collect(Collectors.toList());
    }

    @Override
    public int gramWeight(List<String> words, String gram) {
        return (int) words.stream()
                .filter(checkWord -> checkWord.contains(gram))
                .count();
    }
}
