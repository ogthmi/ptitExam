package com.web.ptitexam.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.web.ptitexam.dto.ClassroomDTO;
import com.web.ptitexam.entity.Classroom;
import com.web.ptitexam.entity.Teacher;

public interface ClassroomService {
    void createClassroom(ClassroomDTO classroomDTO);

    Page<Classroom> findByTeacher(Teacher teacher, String search, Pageable pageable);

    List<Classroom> findAll();

    Classroom findByClassId(String classId);

    void updateClassroomById(String id, ClassroomDTO classroomDTO);

    Boolean existsByClassNameAndTeacher(String className, Teacher teacher);

    void deleteClassroomById(String id);

    void addStudentToClassroom(String classId, String studentId);

    void removeStudentFromClassroom(String classId, String studentId);
}
