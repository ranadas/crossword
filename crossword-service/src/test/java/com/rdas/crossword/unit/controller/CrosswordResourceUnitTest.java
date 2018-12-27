package com.rdas.crossword.unit.controller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.test.TestHazelcastInstanceFactory;
import com.rdas.crossword.CrosswordResource;
import com.rdas.crossword.service.CrosswordSolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CrosswordResource.class)
@ContextConfiguration(classes = {CrosswordResourceUnitTest.TestConfig.class})
public class CrosswordResourceUnitTest {
    @Configuration
//    @Import({CrosswordConfig.class, HazelcastConfiguration.class})
//    @ComponentScan(basePackages = {"com.rdas.crossword.unit.service", "com.rdas.crossword"})
    static class TestConfig {
        @Bean
        @Primary
        public HazelcastInstance crosswordCacheHzInstance() {
            //return Mockito.mock(HazelcastInstance.class);
            //return new TestHazelcastInstanceFactory( 1 ).newHazelcastInstance( config );
            return new TestHazelcastInstanceFactory(1).newHazelcastInstance();
        }

        @Bean
        public CrosswordSolver crosswordSolver(@Autowired HazelcastInstance crosswordCacheHzInstance) {
            //return Mockito.mock(CrosswordSolver.class);
            return new CrosswordSolver(crosswordCacheHzInstance);
        }

        @Bean
        public CrosswordResource crosswordResource(@Autowired CrosswordSolver cs) {
            //return Mockito.mock(CrosswordResource.class);
            return new CrosswordResource(cs);
        }
    }

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CrosswordResource crosswordResource;

    @Autowired
    private CrosswordSolver crosswordSolver;

    @Autowired
    private HazelcastInstance crosswordCacheHzInstance;

    @Before
    public void init() throws Exception {
        List<String> words = Arrays.asList("Apple", "Ananas", "Mango", "Banana", "Beer");

        when(crosswordCacheHzInstance.getList("words-list")).thenReturn(any());
        //when(crosswordCacheHzInstance.getList("words-list")).thenReturn(words);
        Map<Integer, Character> characterPos = new HashMap<>();
        characterPos.put(0, Character.valueOf('p'));
        characterPos.put(2, Character.valueOf('b'));
        int len = 3;

        List<String> keywords = Arrays.asList("pub");

        when(crosswordSolver.search(len, characterPos)).thenReturn(keywords);
    }

    //https://stackoverflow.com/questions/17972428/mock-mvc-add-request-parameter-to-test
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