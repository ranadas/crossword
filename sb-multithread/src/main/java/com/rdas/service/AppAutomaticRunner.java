package com.rdas.service;

import com.rdas.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AppAutomaticRunner implements CommandLineRunner {
    private final GitHubLookupService gitHubLookupService;

    @Autowired
    public AppAutomaticRunner(GitHubLookupService gitHubLookupService) {
        this.gitHubLookupService = gitHubLookupService;
    }

    @Override
    public void run(String... args) throws Exception {
        //gitHubLookupService.getAllUsers();
        runSequential();
        runLoop();
    }

    public void runSequential() throws InterruptedException, ExecutionException {
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        CompletableFuture<User> page1 = gitHubLookupService.findUser("PivotalSoftware");
        CompletableFuture<User> page2 = gitHubLookupService.findUser("CloudFoundry");
        CompletableFuture<User> page3 = gitHubLookupService.findUser("Spring-Projects");

        // Wait until they are all done
        CompletableFuture.allOf(page1, page2, page3).join();

        // Print results, including elapsed time
        log.info("runSequential Elapsed time: " + (System.currentTimeMillis() - start));
        log.info("--> " + page1.get());
        log.info("--> " + page2.get());
        log.info("--> " + page3.get());
    }

    public void runLoop() {
        // Start the clock
        long start = System.currentTimeMillis();
        //https://www.codeaffine.com/2015/11/16/from-arrays-to-streams-and-back-with-java-8/
        List<String> users = Arrays.asList("PivotalSoftware", "CloudFoundry", "Spring-Projects", "ranadas");

        List<CompletableFuture<User>> futureResultList = new ArrayList<>();

        users.parallelStream().forEach(user ->
                {
                    try {
                        futureResultList.add(gitHubLookupService.findUser(user));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );

        CompletableFuture[] futureResultArray = futureResultList.toArray(new CompletableFuture[futureResultList.size()]);

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futureResultArray);

        CompletableFuture<List<User>> finalResults = combinedFuture
                .thenApply(voidd ->
                        futureResultList.stream()
                                .map(future -> future.join())
                                .collect(Collectors.toList()));

        finalResults.thenAccept(result -> System.out.println(result));

        log.info("runLoop Elapsed time: " + (System.currentTimeMillis() - start));
    }
}
