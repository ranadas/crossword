package com.rdas.crossword.config;

import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class CrosswordConfig {

    private final HazelcastInstance hazelcastInstance;
    private final ResourceLoader resourceLoader;

    @Autowired
    public CrosswordConfig(@Qualifier("crosswordCacheHzInstance") HazelcastInstance hazelcast,
                           ResourceLoader resourceLoader) {
        this.hazelcastInstance = hazelcast;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() throws IOException {
        final Resource resource = resourceLoader.getResource("classpath:word-list.txt");
//        List<String> wordsList = Files.readAllLines(Paths.get(fileResource.getURI()), StandardCharsets.UTF_8);
        List<String> list = hazelcastInstance.getList("words-list");
//        wordsList.stream()
//                .forEach(word -> list.add(word));
//        Resource resource = new ClassPathResource("word-list.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        reader.lines().forEach(word -> list.add(word));
    }
}
