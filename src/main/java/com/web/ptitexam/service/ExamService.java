package com.web.ptitexam.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.web.ptitexam.dto.ExamDTO;
import com.web.ptitexam.entity.Classroom;
import com.web.ptitexam.entity.Exam;
import com.web.ptitexam.entity.Student;
import com.web.ptitexam.entity.Teacher;

import java.util.List;

public interface ExamService {

    void createExam(ExamDTO ExamDTO);

    void getCurrentExamInfo();

    ExamDTO getExamList();

    Page<Exam> findByTeacher(Teacher teacher, String search, Pageable pageable);

    Page<Exam> findByStudent(Student student, String search, Pageable pageable);

    Page<Exam> findByTeacherExamId(String examId, String search, Pageable pageable);

    Page<Exam> findByStudentExamId(String examId, String search, Pageable pageable);

    Exam findByExamId(String ExamId);

     // Page<Exam> findByStudentsExzaId(String studentId, String search, Pageable pageable);

    List<Exam> findAll();

    void updateExamById(String id, ExamDTO examDTO);

    void deleteExamById(String id);
}
