package com.web.ptitexam.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.web.ptitexam.entity.UserScore;
import java.util.List;
import java.util.Optional;

public interface UserScoreService {
    List<UserScore> getAllUserScores();
    Optional<UserScore> getUserScoreById(Long id);
    UserScore saveUserScore(UserScore userScore);
    Page<UserScore> findByUserId(Long userId, Pageable pageable);
}

