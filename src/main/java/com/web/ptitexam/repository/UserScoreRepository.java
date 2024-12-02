package com.web.ptitexam.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.web.ptitexam.entity.UserScore;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Long> {
    Page<UserScore> findByUserId(Long userId, Pageable pageable);
}


