package by.dubrovskaya.definition.service;

import java.util.stream.Stream;

public interface StringService {
     String clearTextFromAuxiliarySymbols(String text);

     Stream<String> createStreamOfDistinctWords(String text);
}
