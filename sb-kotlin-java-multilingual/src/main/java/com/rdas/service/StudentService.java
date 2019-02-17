package com.rdas.service;

import com.rdas.entity.Student;
import com.rdas.repo.StudentMyBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentMyBRepository studentRepository;

    public Student getById(Long id) {
        return studentRepository.findById(id);
    }
}
