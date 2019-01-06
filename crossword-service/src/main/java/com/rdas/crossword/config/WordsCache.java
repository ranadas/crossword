package com.rdas.crossword.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

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
        InputStream inputStream = ResourceUtils.getURL("classpath:english.txt").openStream();
        //final Resource fileResource = resourceLoader.getResource("classpath:english.txt");
        //wordList = Files.readAllLines(Paths.get(fileResource.getURI()), StandardCharsets.UTF_8);
        //crosswordSolver.setWords(wordList);

        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
//            String collect = buffer.lines().collect(Collectors.joining("\n"));
            wordList = buffer.lines().collect(Collectors.toList());
        }
    }
}
