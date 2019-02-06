package com.rdas.justice.service;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.rdas.justice.model.JusticeResponse;
import com.rdas.justice.model.Results;
import com.rdas.model.GHUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Profile("justice")
@Slf4j
@Component
public class StartupRunner implements CommandLineRunner {
    private final JusticeDataService justiceDataService;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public StartupRunner(JusticeDataService justiceDataService, MongoTemplate mongoTemplate) {
        this.justiceDataService = justiceDataService;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();
        JusticeResponse justiceResponse = justiceDataService.getJusticeResponse();
        List<Results> results = Arrays.stream(justiceResponse.getResults()).collect(Collectors.toList());
        log.info("** \n\n  response {} \n\n ** ", justiceResponse.toString());

        //loop results and save to db
        // given
        //assertThat(justiceResponse.getResults()).isNotEmpty();
        AtomicLong atomicLong = new AtomicLong();
        List<CompletableFuture<Results>> futureResultsList = new ArrayList<>();
        // when
        results.parallelStream().forEach(result ->
                {
//                    DBObject objectToSave = BasicDBObjectBuilder.start()
//                            .add(atomicLong.incrementAndGet() + "", result)
//                            .get();
                    //mongoTemplate.save(objectToSave, "justicecollection");
                    log.debug("Iterating Result {}", result.getTitle());
                    try {
                        futureResultsList.add(justiceDataService.saveResult(result, atomicLong.incrementAndGet()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        CompletableFuture[] futureResultArray = futureResultsList.toArray(new CompletableFuture[futureResultsList.size()]);
        CompletableFuture.allOf(futureResultArray).join();

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futureResultArray);

        CompletableFuture<List<Results>> finalResults = combinedFuture
                .thenApply(voidd ->
                        futureResultsList.stream()
                                .map(future -> future.join())
                                .collect(Collectors.toList()));

        finalResults.thenAccept(result -> System.out.println(result));

        log.info("runLoop Elapsed time: " + (System.currentTimeMillis() - start));


        // then
        List<Results> collection = mongoTemplate.findAll(Results.class, "justicecollection");
        log.info("Collection {}", collection.size());
        // check size > 0
    }
}
