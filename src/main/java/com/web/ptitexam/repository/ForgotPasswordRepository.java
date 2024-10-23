package com.web.ptitexam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.ptitexam.entity.ForgotPassword;
import com.web.ptitexam.entity.Student;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, String> {
    ForgotPassword findByEmail(String email);

    ForgotPassword findByToken(String token);
}
