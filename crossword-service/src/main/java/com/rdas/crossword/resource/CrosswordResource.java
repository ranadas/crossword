package com.rdas.crossword.resource;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.rdas.crossword.service.CrosswordSolver;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Api(value="Crossword Resource ", description="Operations pertaining to Crossword Solver.")
@Slf4j
@RestController
public class CrosswordResource {
    private final CrosswordSolver crosswordSolver;
    private final HazelcastInstance hazelcast;

    @Autowired
    public CrosswordResource(CrosswordSolver crosswordSolver,
                             @Qualifier("crosswordCacheHzInstance") HazelcastInstance hazelcast) {
        this.crosswordSolver = crosswordSolver;
        this.hazelcast = hazelcast;
    }

    @ApiOperation(value = "View a Hazelcast info, words cache info", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved response"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
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

    @ApiOperation(value = "Search for words from dictionary with given chars and length", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved List of words"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("searchfor")
    public List<String> searchWordWith(@RequestParam(name = "len") Integer lengthOfWord,
                                       @RequestParam(name = "chars") String[] searchChars) {

        Map<Integer, Character> characterPos = new HashMap<>();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Arrays.stream(searchChars).forEach(aChar -> {
            characterPos.put(atomicInteger.getAndIncrement(), Character.valueOf(aChar.charAt(0)));
        });

        IMap<String, Integer> requestStash = hazelcast.getMap("requeststash");
        Integer counter = requestStash.get("REQ");
        if (counter== null) {
            counter = 0;
        }
        requestStash.put("REQ", counter++);

        return crosswordSolver.search(lengthOfWord, characterPos);
    }
}
