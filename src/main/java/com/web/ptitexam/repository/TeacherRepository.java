package com.web.ptitexam.repository;

import com.web.ptitexam.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository <Teacher, String> {
}
