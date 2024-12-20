package com.web.ptitexam.service.implement;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.web.ptitexam.dto.ClassroomDTO;
import com.web.ptitexam.entity.Classroom;
import com.web.ptitexam.entity.Teacher;
import com.web.ptitexam.entity.User;
import com.web.ptitexam.repository.ClassroomRepository;
import com.web.ptitexam.repository.StudentRepository;
import com.web.ptitexam.repository.UserRepository;
import com.web.ptitexam.service.ClassroomService;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository,
            UserRepository userRepository, StudentRepository studentRepository) {
        this.classroomRepository = classroomRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void createClassroom(ClassroomDTO classroomDTO) {
        Classroom classroom = new Classroom();
        classroom.setClassName(classroomDTO.getClassName());
        classroom.setClassId(UUID.randomUUID().toString());
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        User teacher = userRepository.findByUsername(user.getUsername());

        classroom.setTeacher(teacher.getTeacher());
        classroomRepository.save(classroom);
    }

    @Override
    public Page<Classroom> findByTeacher(Teacher teacher, String search, Pageable pageable) {
        if (search.equals("")) {
            return classroomRepository.findByTeacher(teacher, pageable);
        }
        return classroomRepository.findByTeacherAndClassNameContaining(teacher, search, pageable);
    }

    @Override
    public List<Classroom> findAll() {
        return classroomRepository.findAll();
    }

    @Override
    public Classroom findByClassId(String classId) {
        return classroomRepository.findByClassId(classId);
    }

    @Override
    public void updateClassroomById(String id, ClassroomDTO classroomDTO) {
        Classroom classroom = classroomRepository.findByClassId(id);
        classroom.setClassName(classroomDTO.getClassName());
        classroomRepository.save(classroom);
    }

    @Override
    public Boolean existsByClassNameAndTeacher(String className, Teacher teacher) {
        return classroomRepository.existsByClassNameAndTeacher(className, teacher);
    }

    @Override
    public void deleteClassroomById(String id) {
        classroomRepository.deleteById(id);
    }

    @Override
    public void addStudentToClassroom(String classId, String studentId) {
        Classroom classroom = classroomRepository.findByClassId(classId);
        classroom.getStudents().add(studentRepository.findByStudentId(studentId));
        classroomRepository.save(classroom);
    }

    @Override
    public void removeStudentFromClassroom(String classId, String studentId) {
        Classroom classroom = classroomRepository.findByClassId(classId);
        classroom.getStudents().remove(studentRepository.findByStudentId(studentId));
        classroomRepository.save(classroom);
    }

    @Override
    public Page<Classroom> findByStudentsStudentId(String studentId, String search, Pageable pageable) {
        if (search.equals("")) {
            return classroomRepository.findByStudentsStudentId(studentId, pageable);
        }
        return classroomRepository.findByStudentsStudentIdAndClassNameContaining(studentId, search, pageable);
    }

    @Override
    public void leaveClassroom(String classId) {
        Classroom classroom = classroomRepository.findByClassId(classId);
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        User student = userRepository.findByUsername(user.getUsername());
        classroom.getStudents().remove(student.getStudent());
        classroomRepository.save(classroom);
    }
}
