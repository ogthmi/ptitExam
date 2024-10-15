package com.web.ptitexam.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.web.ptitexam.entity.Classroom;
import com.web.ptitexam.entity.Teacher;

public interface ClassroomRepository extends JpaRepository<Classroom, String> {
    Classroom save(Classroom classroom);

    List<Classroom> findAll();

    Page<Classroom> findByTeacher(Teacher teacher, Pageable pageable);

    Page<Classroom> findByTeacherAndClassNameContaining(Teacher teacher, String className, Pageable pageable);

    Classroom findByClassId(String classId);

    Boolean existsByClassNameAndTeacher(String className, Teacher teacher);

}
