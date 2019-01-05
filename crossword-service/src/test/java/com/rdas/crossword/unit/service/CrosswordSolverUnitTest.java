package com.rdas.crossword.unit.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.rdas.crossword.service.CrosswordSolver;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//import java.util.function.Supplier;
//import java.util.stream.DoubleStream;
//import java.util.stream.Stream;

//https://www.baeldung.com/injecting-mocks-in-spring
//https://stackoverflow.com/questions/44200720/difference-between-mock-mockbean-and-mockito-mock
//https://stackoverflow.com/questions/38096629/imap-hazelcast-instance-mock-in-junit
@Ignore
@RunWith(SpringRunner.class)
public class CrosswordSolverUnitTest {

    @Mock
    private HazelcastInstance crosswordCacheHzInstance;

    @MockBean
    private CrosswordSolver crosswordSolver;

    @Mock
    private List<String> wordList;

    private IList list;

    @Before
    public void init() {
        wordList = Arrays.asList("Apple", "Ananas", "Mango", "Banana", "Beer");

        //IList<String> list = words.collect(DistributedCollectors.toIList(uniqueListName()));

        //Mockito.when(hazelcastInstance.getTopic("yourtopic")).thenReturn(Mockito.mock(ITopic.class));
        when(crosswordCacheHzInstance.getList("lisst")).thenReturn(mock(IList.class));
        //when(crosswordCacheHzInstance.getList(anyString())).thenReturn(list);

        final IList list = crosswordCacheHzInstance.getList("lisst");
        list.add("pub");
        list.add("public");
        list.add("publicicity");
        list.add("pubard");
    }

    @Test
    public void contextLoads() {
        assertThat(crosswordSolver).isNotNull();
    }

    @Test
    public void searchWithMap() {
        Integer lengthOfWord = 6;
        String[] searchChars = {"p", ".", ".", "p", ".", "."};

        Map<Integer, Character> characterPos = new HashMap<>();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Arrays.stream(searchChars).forEach(aChar -> {
            characterPos.put(atomicInteger.getAndIncrement(), Character.valueOf(aChar.charAt(0)));
        });

        assertThat(crosswordSolver.search(lengthOfWord, characterPos)).isNotNull();
    }
}