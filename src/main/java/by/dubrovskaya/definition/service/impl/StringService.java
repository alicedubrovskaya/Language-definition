package by.dubrovskaya.definition.service.impl;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Stream;

@Service
public class StringService implements by.dubrovskaya.definition.service.StringService {
    private static final String DISTINCT_WORDS_REGEX = "[\\s\n]";
    private static final String CLEAR_TEXT_REGEX = "[,.!?:()—#\\-\\[\\]]";

    @Override
    public String clearTextFromAuxiliarySymbols(String text) {
        return text.replace(CLEAR_TEXT_REGEX, "");
    }

    @Override
    public Stream<String> createStreamOfDistinctWords(String text) {
        return Arrays.stream(text.split(DISTINCT_WORDS_REGEX));
    }
}
