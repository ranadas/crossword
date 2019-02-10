package com.rdas.service;

import com.rdas.entity.Student;
import com.rdas.repo.StudentMyBRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class HzcastService {

    @Autowired
    private StudentMyBRepository studentMyBRepository;

    @PostConstruct
    public void init() {
        IntStream.range(0, 100000)
                .parallel()
                .forEach(nbr -> {
                            Student student = new Student(Long.valueOf(nbr),
                                    RandomStringUtils.randomAlphabetic(6),
                                    RandomStringUtils.randomAscii(8));
                            //System.out.printf(student.toString());
                            studentMyBRepository.insert(student);
                        }
                );
    }

    public String sample() {
        // TODO : remove this call in a bit
        Student byId = studentMyBRepository.findById(10001L);
        int count = studentMyBRepository.findAll().size();

        List<Long> collectedIds = studentMyBRepository.findAll()
                .stream()
                .map(Student::getId)
                .filter(id -> id < 5000)
                .collect(Collectors.toList());
        List<Student> students = studentMyBRepository.selectByKeys(collectedIds);

        return "Sample + "+count;
    }
}
