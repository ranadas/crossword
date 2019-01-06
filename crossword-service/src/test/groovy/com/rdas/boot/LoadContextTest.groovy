//package com.rdas.boot
//
//import com.rdas.crossword.bdd.IntegrationTestConfig
//import com.rdas.crossword.resource.CrosswordResource
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import spock.lang.Narrative
//import spock.lang.Specification
//import spock.lang.Title
//
////https://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4
//@Title("Application Specification, loading all contexts")
//@Narrative("Specification which beans are expected")
//@SpringBootTest(classes = IntegrationTestConfig.class)
////@ContextConfiguration(classes = { CrosswordSolverTest.TestConfig.class})
//class LoadContextTest extends Specification {
//
//    @Autowired
//    private CrosswordResource webController
//
//    def "when context is loaded then all expected beans are created"() {
//        expect: "the CrosswordResource is created"
//        webController
//    }
//}