package com.web.ptitexam.repository;

import com.web.ptitexam.entity.Teacher;
import com.web.ptitexam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository <Teacher, String> {
    Optional<Teacher> findByUser(User user);
}
