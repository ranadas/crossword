package com.rdas.crossword.rest;

import com.rdas.crossword.resource.CrosswordResource;
import com.rdas.crossword.config.CrosswordConfig;
import com.rdas.crossword.config.HazelcastConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//https://www.blazemeter.com/blog/spring-boot-rest-api-unit-testing-with-junit
@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(CrosswordResource.class)
@ContextConfiguration(classes = {CrosswordResourceTest.TestConfig.class})
public class CrosswordResourceTest {
    @Configuration
    @Import({CrosswordConfig.class, HazelcastConfiguration.class})
    @ComponentScan(basePackages = {"com.rdas.crossword.unit.service", "com.rdas.crossword"})
    static class TestConfig {
    }
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CrosswordResource crosswordResource;

    @Before
    public void init() throws Exception {
        final Resource fileResource = resourceLoader.getResource("classpath:english.txt");
        List<String> wordList = Files.readAllLines(Paths.get(fileResource.getURI()), StandardCharsets.UTF_8);
        assertThat(wordList).isNotNull();
    }

    @Test
    public void contextLoads() {
        assertThat(resourceLoader).isNotNull();
    }

    @Test
    public void testSearchCall() throws Exception {
        mvc.perform(get("/search")
                .param("len", "3")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                //.andExpect(jsonPath("city", is(arrival.getCity())))
        ;
    }
}