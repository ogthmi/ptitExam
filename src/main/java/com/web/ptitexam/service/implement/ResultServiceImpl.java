package com.web.ptitexam.service.implement;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.web.ptitexam.dto.ResultDTO;
import com.web.ptitexam.entity.Result;
import com.web.ptitexam.entity.Student;
import com.web.ptitexam.repository.ResultRepository;
import com.web.ptitexam.service.ResultService;

@Service
public class ResultServiceImpl implements ResultService {
    private final ResultRepository resultRepository;

    public ResultServiceImpl(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Override
    public List<Result> findByExamIdAndStudent(String examId, Student student) {
        return resultRepository.findByExamExamIdAndStudent(examId, student);
    }

    @Override
    public void createResult(ResultDTO resultDTO) {
        Result result = new Result();
        result.setResultId(UUID.randomUUID().toString());
        result.setExam(resultDTO.getExam());
        result.setStudent(resultDTO.getStudent());
        result.setCorrectAnswerCount(resultDTO.getCorrectAnswerCount());
        result.setScore(resultDTO.getScore());

        resultRepository.save(result);
    }

}
