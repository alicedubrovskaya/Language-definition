package by.dubrovskaya.definition.service;

import java.util.List;
import java.util.Map;

public interface WordService {
    Map<String, Integer> buildNgramFrequencyMap(String text);

    Map<String, Integer> buildNgramLimitedAndSortedFrequencyMap(Map<String, Integer> nGramStructure,
                                                                Integer maxSize);

    List<String> receiveClearedDistinctWords(String dirtyText);


    int gramWeight(List<String> words, String gram);
}
