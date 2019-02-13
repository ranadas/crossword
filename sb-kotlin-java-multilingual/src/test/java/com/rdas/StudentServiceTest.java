package com.rdas;

import com.rdas.entity.Student;
import com.rdas.repo.StudentMyBRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private StudentMyBRepository empService;


    @Test
    public void givenMockingIsDoneByMockito_whenGetIsCalled_shouldReturnMockedObject() {
        Long id = 1L;
        Student student = new Student(1L, "Eric Simmons", "E001");
        Mockito
                .when(restTemplate.getForEntity("http://localhost:8080/employee/E001", Student.class))
          .thenReturn(new ResponseEntity(student, HttpStatus.OK));

        Student student1 = empService.findById(id);
        Assert.assertEquals(student, student1);
    }
}
