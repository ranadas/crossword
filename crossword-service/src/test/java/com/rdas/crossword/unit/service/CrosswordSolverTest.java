package com.rdas.crossword.unit.service;

import com.rdas.crossword.bdd.IntegrationTestConfig;
import com.rdas.crossword.service.CrosswordSolver;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {IntegrationTestConfig.class})
public class CrosswordSolverTest {

    @Autowired
    private CrosswordSolver crosswordSolver;

    @Test
    public void contextLoads() {
        assertThat(crosswordSolver).isNotNull();
    }

    @Test
    public void searchWithMap() {
        Integer lengthOfWord = 6;
        String[] searchChars = {"p", ".", ".", "p", ".", "."};

        Map<Integer, Character> characterPos = new HashMap<>();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Arrays.stream(searchChars).forEach(aChar -> {
            characterPos.put(atomicInteger.getAndIncrement(), Character.valueOf(aChar.charAt(0)));
        });

        List<String> search = crosswordSolver.search(lengthOfWord, characterPos);

        assertThat(search).isNotNull();

    }
    @Test
    public void searchFor4Length() {
        Integer lengthOfWord = 9;
        String[] searchChars = {"a", "c", ".", "d", ".", ".", ".", "."};

        Map<Integer, Character> characterPos = new HashMap<>();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Arrays.stream(searchChars).forEach(aChar -> {
            characterPos.put(atomicInteger.getAndIncrement(), Character.valueOf(aChar.charAt(0)));
        });

        List<String> search = crosswordSolver.search(lengthOfWord, characterPos);

        assertThat(search).isNotNull();

    }
}