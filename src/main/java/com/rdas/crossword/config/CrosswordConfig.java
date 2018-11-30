package com.rdas.crossword.config;

import com.hazelcast.core.HazelcastInstance;
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
public class CrosswordConfig {

    private final HazelcastInstance hazelcastInstance;
    private ResourceLoader resourceLoader;

    @Autowired
    public CrosswordConfig(HazelcastInstance hazelcastInstance, ResourceLoader resourceLoader) {
        this.hazelcastInstance = hazelcastInstance;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() throws Exception {
        final Resource fileResource = resourceLoader.getResource("classpath:word-list.txt");
        List<String> wordsList = Files.readAllLines(Paths.get(fileResource.getURI()), StandardCharsets.UTF_8);
        List<String> list = hazelcastInstance.getList("words-list");
        wordsList.stream()
                .forEach(word -> list.add(word));
    }
}
