package com.rdas.crossword.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CrosswordSolver {
    private List<String> wordList;


    public void setWords(final List<String> wordList) {
        this.wordList = wordList;
    }

    public List<String> search(int length, Map<Integer, Character> characterPos) {
        Pattern pattern = buildPattern(length, characterPos);

        List<String> collectedWord = wordList.stream()
                .filter(word -> word.length() == length)
                .filter(word -> match(word, pattern))
                .collect(Collectors.toList());
        return collectedWord;
    }

    private boolean match(String word, Pattern pattern) {
        log.info("testing {} with {} ", word, pattern.toString());
        Matcher matcher = pattern.matcher(word);
        boolean found = matcher.find();
        log.info("\n {}  is found ? {} \n", word, found);
        return found;
    }

    private Pattern buildPattern(int length, Map<Integer, Character> characterPos) {
        StringBuilder placeHolder = new StringBuilder(buildStringWithPlaceholder(length));

        characterPos.forEach((k, v) -> placeHolder.setCharAt(k, v));

        log.info("\nRegExp pattern is : {}", placeHolder.toString());
        return Pattern.compile(placeHolder.toString(), Pattern.CASE_INSENSITIVE);
    }

    private String buildStringWithPlaceholder(int length) {
        char[] charArray = new char[length];
        Arrays.fill(charArray, '.');
        return new String(charArray);
    }
}
