package com.web.ptitexam.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.web.ptitexam.entity.UserScore;
import com.web.ptitexam.repository.UserScoreRepository;
import com.web.ptitexam.service.UserScoreService;

import java.util.List;
import java.util.Optional;

@Service
public class UserScoreServiceImpl implements UserScoreService {
    @Autowired
    private UserScoreRepository userScoreRepository;

    @Override
    public List<UserScore> getAllUserScores() {
        return userScoreRepository.findAll();
    }

    @Override
    public Optional<UserScore> getUserScoreById(Long id) {
        return userScoreRepository.findById(id);
    }

    @Override
    public UserScore saveUserScore(UserScore userScore) {
        return userScoreRepository.save(userScore);
    }

    @Override
    public Page<UserScore> findByUserId(Long userId, Pageable pageable) {
        return userScoreRepository.findByUserId(userId, pageable);
    }
}

