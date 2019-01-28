package com.rdas.ghservice;

import com.rdas.model.GHUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Profile("github")
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
        //runSequential();
        runLoop();
    }

    public void runSequential() throws InterruptedException, ExecutionException {
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        CompletableFuture<GHUser> page1 = gitHubLookupService.findUser("PivotalSoftware", 1L);
        CompletableFuture<GHUser> page2 = gitHubLookupService.findUser("CloudFoundry", 2L);
        CompletableFuture<GHUser> page3 = gitHubLookupService.findUser("Spring-Projects", 3L);

        // Wait until they are all done
        CompletableFuture.allOf(page1, page2, page3).join();

        // Print results, including elapsed time
        log.info("runSequential Elapsed time: " + (System.currentTimeMillis() - start));
        log.info("--> " + page1.get());
        log.info("--> " + page2.get());
        log.info("--> " + page3.get());
    }

    public void runLoop() throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();
        //https://www.codeaffine.com/2015/11/16/from-arrays-to-streams-and-back-with-java-8/
        //List<String> users = Arrays.asList("PivotalSoftware", "CloudFoundry", "Spring-Projects", "ranadas");
        List<GHUser> users = gitHubLookupService.findUsers();
        List<CompletableFuture<GHUser>> futureResultList = new ArrayList<>();
        AtomicLong atomicLong = new AtomicLong();
        users.parallelStream().forEach(user ->
                {

                    log.debug("Iterating user {}", user.getLogin());
                    try {
                        futureResultList.add(gitHubLookupService.findUser(user.getLogin(), atomicLong.incrementAndGet()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );

        CompletableFuture[] futureResultArray = futureResultList.toArray(new CompletableFuture[futureResultList.size()]);
        CompletableFuture.allOf(futureResultArray).join();

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futureResultArray);

        CompletableFuture<List<GHUser>> finalResults = combinedFuture
                .thenApply(voidd ->
                        futureResultList.stream()
                                .map(future -> future.join())
                                .collect(Collectors.toList()));

        finalResults.thenAccept(result -> System.out.println(result));

        log.info("runLoop Elapsed time: " + (System.currentTimeMillis() - start));
    }
}
