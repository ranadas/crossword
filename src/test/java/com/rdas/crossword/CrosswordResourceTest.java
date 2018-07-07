package com.rdas.crossword;

import com.rdas.crossword.service.CrosswordSolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrosswordResourceTest {
    @Autowired
    private CrosswordSolver crosswordSolver;
    @Autowired
    private ResourceLoader resourceLoader;

    private List<String> wordList;


    @Before
    public void init() throws Exception {
        final Resource fileResource = resourceLoader.getResource("classpath:english.txt");
        wordList = Files.readAllLines(Paths.get(fileResource.getURI()), StandardCharsets.UTF_8);
        crosswordSolver.setWords(wordList);

        assertThat(wordList).isNotNull();
    }

    @Test
    public void contextLoads() {
        assertThat(crosswordSolver).isNotNull();
        assertThat(resourceLoader).isNotNull();
    }

    @Test
    public void searchWithMap() {
        Integer lengthOfWord = 6;
        String[] searchChars = {"p", ".", ".", "p", ".", "."};

        Map<Integer, Character> characterPos = new HashMap<>();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Arrays.stream(searchChars).forEach(aChar -> {
            characterPos.put(atomicInteger.getAndIncrement(), new Character(aChar.charAt(0)));
        });

        List<String> search = crosswordSolver.search(lengthOfWord, characterPos);

        assertThat(search).isNotNull();

    }
}