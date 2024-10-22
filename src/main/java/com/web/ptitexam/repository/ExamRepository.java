package com.web.ptitexam.repository;

import com.web.ptitexam.entity.Exam;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, String> {
    Optional<Exam> findByExamId(String examId);
}