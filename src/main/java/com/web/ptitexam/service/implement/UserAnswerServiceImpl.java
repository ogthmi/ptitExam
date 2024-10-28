package com.web.ptitexam.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.web.ptitexam.entity.UserAnswer;
import com.web.ptitexam.repository.UserAnswerRepository;
import com.web.ptitexam.service.UserAnswerService;

@Service
public class UserAnswerServiceImpl implements UserAnswerService {
    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Override
    public void saveUserAnswer(UserAnswer userAnswer) {
        userAnswerRepository.save(userAnswer);
    }

    @Override
    public Page<UserAnswer> findByUserId(Long userId, Pageable pageable) {
        return userAnswerRepository.findByUserId(userId, pageable);
    }

    @Override
    public UserAnswer findById(Long id) {
        return userAnswerRepository.findById(id).orElse(null);
    }
}
