package com.rdas.service;

import com.rdas.model.GHResponse;
import com.rdas.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class GitHubLookupService {

    private final RestTemplate restTemplate;

    public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    @Async
    public CompletableFuture<User> findUser(String user) throws InterruptedException {
        log.info("Looking up " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        User results = restTemplate.getForObject(url, User.class);

        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(results);
    }

    //https://stackoverflow.com/questions/6349421/how-to-use-jackson-to-deserialise-an-array-of-objects
    @Async
    public void getAllUsers() throws Exception {
        //String url = "https://api.github.com/search/users?q=repos:%3E12+followers:%3C1000&location:uk+language:python&page=1&per_page=10";
        String url = "https://api.github.com/users?since=135>; rel=\"next\"";

        log.info("Looking up users" );
        GHResponse resp = restTemplate.getForObject(url, GHResponse.class);
        //String resp = restTemplate.getForObject(url, String.class);
        log.info(resp.toString());

    }
}
