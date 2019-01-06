//package com.rdas.crossword.resource
//
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
//import org.springframework.boot.test.context.TestConfiguration
//import org.springframework.context.annotation.ComponentScan
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers
//import spock.lang.Narrative
//import spock.lang.Specification
//import spock.lang.Title
//import org.springframework.test.context.junit4.SpringRunner;
//import com.rdas.crossword.bdd.IntegrationTestConfig;
//
////https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html
////https://www.baeldung.com/spring-boot-testing
//@Title("CrosswordResource Controller Specification")
//@Narrative("The Specification of the behaviour of the CrosswordResource.")
//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//@WebMvcTest//(controllers = CrosswordResource.class)
////@TestConfiguration("IntegrationTestConfig")
//class CrosswordResourceSpec extends Specification {
//
//    @TestConfiguration
//    @ComponentScan(basePackages = "com.rdas.crossword")
//    static class IntTestConfig {
//
//    }
//    @Autowired
//    private MockMvc mvc
//
//    def "when get is performed then the response has status 200 and content contains 'Hello !'"() {
//        expect: "Status is 200 and the response is 'Hello world!'"
//        mvc.perform(MockMvcRequestBuilders.get("/hello"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn().response.contentAsString == "Hello "
//    }
//}