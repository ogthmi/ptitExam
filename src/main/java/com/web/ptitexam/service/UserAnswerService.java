package com.web.ptitexam.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.web.ptitexam.entity.UserAnswer;

public interface UserAnswerService {
    void saveUserAnswer(UserAnswer userAnswer);
    Page<UserAnswer> findByUserId(Long userId, Pageable pageable);
    UserAnswer findById(Long id);
}
