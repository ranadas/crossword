package com.rdas.crossword.resource;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;
import com.rdas.crossword.config.WordsCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

@Slf4j
@RestController
@Api(value="DignosticsResource Management", description="Operations pertaining to System Diagnostivs")
public class DignosticsResource {
    private WordsCache wordsCache;
    private HazelcastInstance hazelcast;

    @Autowired
    public DignosticsResource(WordsCache wordsCache, @Qualifier("crosswordCacheHzInstance") HazelcastInstance hazelcast) {
        this.wordsCache = wordsCache;
        this.hazelcast = hazelcast;
    }

    @ApiOperation(value = "View a Hazelcast info, words cache info", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved response"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("hello")
    @ResponseStatus(HttpStatus.OK)
    public final ResponseEntity<?> hello() throws UnknownHostException {
        ClientConfig config = new ClientConfig();
        HazelcastInstance hzClient = HazelcastClient.newHazelcastClient(config);

        Set<Member> members = hazelcast.getCluster().getMembers();
        members.parallelStream().forEach(System.out::println);

        log.info("\n\n HZ CLIENT NAME : {}\n", hzClient.getName());
        String format = String.format(" HZ CLIENT NAME :%s with cache size %d ", hzClient.getName(), wordsCache.getWordList().size());
        //"Hello! You can find me in " + InetAddress.getLocalHost().getHostAddress()  + format;
        String response = String.format("You can find me in %s, and %s \n", InetAddress.getLocalHost().getHostAddress(), format);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
