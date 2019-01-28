package com.rdas;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.rdas.justice.model.JusticeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationMain.class})
@AutoConfigureWebClient
public class MongoDbSpringIntegrationTest {
    @DisplayName("Given object When save object using MongoDB template Then object can be found")
    @Test
    public void test(@Autowired MongoTemplate mongoTemplate) {
        // given
        DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("key", "value")
                .get();

        // when
        mongoTemplate.save(objectToSave, "collection");

        // then
        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
                .containsOnly("value");
    }


    @DisplayName("Given JusticeResponse When saved, then object can be found back.")
    @Test
    public void testSaveretriveJusticeResponse(@Autowired MongoTemplate mongoTemplate) throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/justice5.json");
        String resp = inputStream2String(is);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JusticeResponse justiceResponse = mapper.readValue(resp, JusticeResponse.class);


        //loop results and save to db
        // given
        assertThat(justiceResponse.getResults()).isNotEmpty();
        AtomicLong atomicLong = new AtomicLong();

        // when
        Arrays.stream(justiceResponse.getResults()).forEach(result ->
                {
                    DBObject objectToSave = BasicDBObjectBuilder.start()
                            .add(atomicLong.incrementAndGet() + "", result)
                            .get();
                    mongoTemplate.save(objectToSave, "justicecollection");
                }
        );

        // then
        List<DBObject> collection = mongoTemplate.findAll(DBObject.class, "justicecollection");
        assertThat(collection.size()).isEqualTo(20);
    }

    public String inputStream2String(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
}
