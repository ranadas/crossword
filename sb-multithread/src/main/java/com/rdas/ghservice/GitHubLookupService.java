package com.rdas.ghservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.rdas.model.GHUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class GitHubLookupService {

    @Autowired
    private RestOperations restOperations;
    private final RestTemplate restTemplate;

    public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public CompletableFuture<GHUser> findUser(String user, Long counter) throws InterruptedException {
        log.info("Looking up {}  at {}" , user, counter);
        String url = String.format("https://api.github.com/users/%s", user);
        try {
            GHUser result = restOperations.getForObject(url, GHUser.class);
            result.setCounter(counter);
            log.info("found {}", result);
            // Artificial delay of 1s for demonstration purposes
            Thread.sleep(1000L);

            return CompletableFuture.completedFuture(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(new GHUser());
    }

    //https://stackoverflow.com/questions/6349421/how-to-use-jackson-to-deserialise-an-array-of-objects
    //@Async
    public List<GHUser> findUsers() throws Exception {
        //String url = "https://api.github.com/search/users?q=repos:%3E12+followers:%3C1000&location:uk+language:python&page=1&per_page=10";
        String url = "https://api.github.com/users?since=135>; rel=\"next\"";

        log.info("Looking up all github users" );
        //GHResponse resp = restTemplate.getForObject(url, GHResponse.class);
        String resp = restOperations.getForObject(url, String.class);
        //String resp = restTemplate.getForObject(url, String.class);
        log.info(resp.toString());
        ObjectMapper mapper = new ObjectMapper();
        CollectionType javaType = mapper.getTypeFactory().constructCollectionType(List.class, GHUser.class);
        List<GHUser> ghUsers = mapper.readValue(resp, javaType);
        return ghUsers;
        //return CompletableFuture.completedFuture(ghUsers);
    }
}