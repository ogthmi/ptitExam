package com.web.ptitexam.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.web.ptitexam.dto.ResultDTO;
import com.web.ptitexam.entity.Result;
import com.web.ptitexam.entity.Student;

public interface ResultService {
    List<Result> findByExamIdAndStudent(String examId, Student student);

    void createResult(ResultDTO resultDTO);

    Page<Result> findByStudent(Student student, Pageable pageable);

    List<Result> findAll();

    Result findByResultId(String resultId);

    void removeResult(Result result);
}
