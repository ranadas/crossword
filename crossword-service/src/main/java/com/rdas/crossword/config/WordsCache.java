package com.rdas.crossword.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
@Deprecated
// Use hazelcast instance to get the cache instead.
public class WordsCache {

    @Autowired
    private ResourceLoader resourceLoader;

    @Getter
    private List<String> wordList;

    @PostConstruct
    public void init() throws Exception {
        final Resource fileResource = resourceLoader.getResource("classpath:english.txt");
        wordList = Files.readAllLines(Paths.get(fileResource.getURI()), StandardCharsets.UTF_8);
        //crosswordSolver.setWords(wordList);
    }
}
