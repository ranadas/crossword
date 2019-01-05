package com.rdas.crossword.resource;

import com.rdas.crossword.service.CrosswordSolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
public class CrosswordResource {
    private CrosswordSolver crosswordSolver;

    @Autowired
    public CrosswordResource(CrosswordSolver crosswordSolver) {
        this.crosswordSolver = crosswordSolver;
    }

    @GetMapping("search")
    public List<String> searchWord(@RequestParam(name = "len", required = true) Integer lengthOfWord) {
        Map<Integer, Character> characterPos = new HashMap<>();
        characterPos.put(0, Character.valueOf('p'));
        characterPos.put(2, Character.valueOf('b'));
        //CrosswordSolver crosswordSolver = applicationContext.getBean(CrosswordSolver.class, wordList);

        List<String> wordsFound = crosswordSolver.search(lengthOfWord, characterPos);
        wordsFound.forEach(System.out::println);
        return wordsFound;
    }

    @GetMapping("searchfor")
    public List<String> searchWordWith(@RequestParam(name = "len") Integer lengthOfWord,
                                       @RequestParam(name = "chars") String[] searchChars) {

        Map<Integer, Character> characterPos = new HashMap<>();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Arrays.stream(searchChars).forEach(aChar -> {
            characterPos.put(atomicInteger.getAndIncrement(), Character.valueOf(aChar.charAt(0)));
        });

        return crosswordSolver.search(lengthOfWord, characterPos);
    }
}
