package com.web.ptitexam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.ptitexam.entity.Result;
import java.util.List;
import com.web.ptitexam.entity.Student;

@Repository
public interface ResultRepository extends JpaRepository<Result, String> {

    List<Result> findByExamExamIdAndStudent(String examId, Student student);

    Result save(Result result);
}