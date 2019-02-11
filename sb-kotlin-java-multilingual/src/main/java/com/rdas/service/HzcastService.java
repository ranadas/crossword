package com.rdas.service;

import com.hazelcast.core.HazelcastInstance;
import com.rdas.entity.Student;
import com.rdas.repo.StudentMyBRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class HzcastService {
    private final HazelcastInstance hazelcastInstance;
    private StudentMyBRepository studentMyBRepository;

    @Autowired
    public HzcastService(@Qualifier("crosswordCacheHzInstance") HazelcastInstance hazelcastInstance,
                         StudentMyBRepository studentMyBRepository) {
        this.hazelcastInstance = hazelcastInstance;
        this.studentMyBRepository = studentMyBRepository;
    }

    @PostConstruct
    public void init() {
        List<Student> studentList = hazelcastInstance.getList("students-list");
        IntStream.range(0, 100000)
                .parallel()
                .forEach(nbr -> {
                            Student student = new Student(Long.valueOf(nbr),
                                    RandomStringUtils.randomAlphabetic(6),
                                    RandomStringUtils.randomNumeric(8));
                            studentList.add(student);
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
                .filter(id -> id < 10000)
                .collect(Collectors.toList());
        List<Student> students = studentMyBRepository.selectByKeys(collectedIds);

        return "Find all count  = " + count;
    }
}
