package com.web.ptitexam.service;

import java.util.List;

import com.web.ptitexam.dto.ResultDTO;
import com.web.ptitexam.entity.Result;
import com.web.ptitexam.entity.Student;

public interface ResultService {
    List<Result> findByExamIdAndStudent(String examId, Student student);

    void createResult(ResultDTO resultDTO);
}
