package com.rdas.crossword.resource;

import com.rdas.crossword.config.WordsCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@RestController
public class DignosticsResource {
    private WordsCache wordsCache;

    @Autowired
    public DignosticsResource(WordsCache wordsCache) {
        this.wordsCache = wordsCache;
    }

    @GetMapping("hello")
    @ResponseStatus(HttpStatus.FOUND)
    public final String hello() throws UnknownHostException {
        return "Hello! You can find me in " + InetAddress.getLocalHost().getHostAddress();
    }
}
