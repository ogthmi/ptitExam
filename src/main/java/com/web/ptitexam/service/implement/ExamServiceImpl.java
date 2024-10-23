package com.web.ptitexam.service.implement;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.web.ptitexam.dto.ExamDTO;
import com.web.ptitexam.entity.Exam;
import com.web.ptitexam.entity.Teacher;
import com.web.ptitexam.entity.User;
import com.web.ptitexam.repository.ClassroomRepository;
import com.web.ptitexam.repository.ExamRepository;
import com.web.ptitexam.repository.StudentRepository;
import com.web.ptitexam.repository.TeacherRepository;
import com.web.ptitexam.repository.UserRepository;
import com.web.ptitexam.service.ExamService;

@Service
public class ExamServiceImpl implements ExamService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;

    public ExamServiceImpl(TeacherRepository teacherRepository, UserRepository userRepository,
            StudentRepository studentRepository, ExamRepository examRepository) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.examRepository = examRepository;
    }

    @Override
    public void createExam(ExamDTO ExamDTO) {
        Exam exam = new Exam();
        exam.setExamTitle(ExamDTO.getExamTitle());
        exam.setExamId(UUID.randomUUID().toString());
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        /// TODO: save question
        User teacher = userRepository.findByUsername(user.getUsername());
        exam.setTeacher(teacher.getTeacher());
        examRepository.save(exam);
    }

    @Override
    public void getCurrentExamInfo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentExamInfo'");
    }

    @Override
    public ExamDTO getExamList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Exam> findByTeacher(Teacher teacher, String search, Pageable pageable) {
        if (search.equals("")) {
            return examRepository.findByTeacher(teacher, pageable);
        }
        return examRepository.findByTeacherAndClassNameContaining(teacher, search, pageable);
    }

    @Override
    public Exam findByExamId(String ExamId) {
        return examRepository.findByExamId(ExamId);
    }

    @Override
    public Page<Exam> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public void updateExamById(String id, ExamDTO examDTO) {
        Exam exam = examRepository.findByExamId(id);
        exam.setExamTitle(examDTO.getExamTitle());
        examRepository.save(exam);
    }

}
