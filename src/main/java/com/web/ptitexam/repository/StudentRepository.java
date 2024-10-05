package com.web.ptitexam.repository;

import com.web.ptitexam.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository <Student, String> {
}
