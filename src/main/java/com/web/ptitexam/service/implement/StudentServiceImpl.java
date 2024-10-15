package com.web.ptitexam.service.implement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.ptitexam.entity.Classroom;
import com.web.ptitexam.entity.Student;
import com.web.ptitexam.repository.StudentRepository;
import com.web.ptitexam.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student findByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    @Override
    public Page<Student> findByClassrooms(Classroom classroom, Pageable pageable) {
        return studentRepository.findByClassrooms(classroom, pageable);
    }
}
