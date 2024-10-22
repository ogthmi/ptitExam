package com.web.ptitexam.service.implement;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.web.ptitexam.dto.ExamDTO;
import com.web.ptitexam.entity.Exam;
import com.web.ptitexam.service.ExamService;
@Service
public class ExamServiceImpl implements ExamService {

    @Override
    public void createExam(ExamDTO ExamDTO) {
        Exam exam = new Exam();
        exam.setExamId(UUID.randomUUID().toString());
        exam.setExamAuthor(ExamDTO.getExamAuthor());
        exam.setExamDuration(ExamDTO.getExamDuration());
        exam.setExamTitle(ExamDTO.getExamTitle());
        exam.setClassAssigned(ExamDTO.getClassAssigned());
        exam.setQuestionNumber(ExamDTO.getQuestionNumber());
        exam.setQuestionIdList(ExamDTO.getQuestionIdList());
    }

    @Override
    public void getCurrentExamInfo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentExamInfo'");
    }
    @Override
    public ExamDTO getExamList() {
        // TODO Auto-generated method stub
        return null;
    }
    

}
