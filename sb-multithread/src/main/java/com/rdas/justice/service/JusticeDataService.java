package com.rdas.justice.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.rdas.justice.model.JusticeResponse;
import com.rdas.justice.model.Results;
import com.rdas.model.GHUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JusticeDataService {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public JusticeDataService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired
    private MongoClient mongoClient;

    @PostConstruct
    public void init() throws Exception {
//        Resource resource = new ClassPathResource("justice5.json");
//        File file = resource.getFile();
//        InputStream in = getClass().getResourceAsStream("classpath:resources/justice5.json");
//        System.out.println(inputStream2String(in));

        //String employees = new String(Files.readAllBytes(Paths.get(resourceFile.getURI())));
        //System.out.println(employees);

//        File file = ResourceUtils.getFile("classpath:justice5.json");
//        InputStream in = new FileInputStream(file);
//        String resp = inputStream2String(in);
//         in = getClass().getResourceAsStream("/justice5.json");
//
//        Resource resource = new ClassPathResource("justice5.json");
//        String path = resource.getURL().getPath();
//        System.out.println(path);
    }

    public JusticeResponse getJusticeResponse() throws IOException {
        //InputStream is = this.getClass().getResourceAsStream("resources/justice5.json");
        ClassPathResource classPathResource = new ClassPathResource("/justice5.json");
        InputStream is = classPathResource.getInputStream();
        String resp = inputStream2String(is);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JusticeResponse justiceResponse = mapper.readValue(resp, JusticeResponse.class);
        return justiceResponse;

    }

    public String inputStream2String(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

    //
    @Async
    public CompletableFuture<Results> saveResult(Results objectToSave, Long counter) throws InterruptedException {
        log.info("Saving Results {}  at {}" , objectToSave.getTitle(), counter);
        // Start the clock
        long start = System.currentTimeMillis();
        try {
            // Artificial delay of 1s for demonstration purposes
            Thread.sleep(1000L);
            mongoTemplate.save(objectToSave, "justicecollection");
            //return CompletableFuture.completedFuture(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Saving to Mongo time: : " + (System.currentTimeMillis() - start));
        return CompletableFuture.completedFuture(objectToSave);
    }
}
