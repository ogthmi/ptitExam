package com.web.ptitexam.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.ptitexam.constant.Constant;
import com.web.ptitexam.dto.AnswerDTO;
import com.web.ptitexam.dto.ExamDTO;
import com.web.ptitexam.dto.QuestionDTO;
import com.web.ptitexam.dto.ResultDTO;
import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.entity.Classroom;
import com.web.ptitexam.entity.Exam;
import com.web.ptitexam.entity.Question;
import com.web.ptitexam.entity.User;
import com.web.ptitexam.service.ClassroomService;
import com.web.ptitexam.service.ExamService;
import com.web.ptitexam.service.QuestionService;
import com.web.ptitexam.service.ResultService;
import com.web.ptitexam.service.StudentService;
import com.web.ptitexam.service.UserService;

@Controller
public class ExamController {

    private final UserService userService;
    private final ClassroomService classroomService;
    private final ExamService examService;
    private final QuestionService questionService;
    private final ResultService resultService;

    private String examTitle;
    private int examDuration;
    private int questionCount;

    public ExamController(UserService userService, ClassroomService classroomService,
            StudentService studentService, ExamService examService, QuestionService questionService,
            ResultService resultService) {
        this.userService = userService;
        this.classroomService = classroomService;
        this.examService = examService;
        this.questionService = questionService;
        this.resultService = resultService;
    }

    @GetMapping(value = Constant.PAGE_TEACHER_CLASSROOM + "/{id}/exam/create")
    public String showExamCreate(Model model, @PathVariable("id") String id) {
        try {
            UserDTO currentUser = userService.getCurrentUser();
            Classroom classroom = classroomService.findByClassId(id);
            model.addAttribute("examForm", new ExamDTO());

            model.addAttribute("userDTO", currentUser);
            model.addAttribute("classroom", classroom);
            if (classroom == null) {
                return "redirect:/teacher/classroom";
            }

        } catch (Exception e) {
        }
        return Constant.PAGE_TEACHER_UPDATE_EXAM;
    }

    @GetMapping(value = Constant.PAGE_TEACHER_CLASSROOM + "/{id}/exam/create/question")
    public String createExamQuestionPage(Model model,
            @PathVariable("id") String id,
            @RequestParam("examTitle") String examTitle,
            @RequestParam("examDuration") int examDuration,

            @RequestParam("questionCount") int questionCount,
            RedirectAttributes redirectAttributes) {
        try {
            // System.out.println(examTitle + examDuration + questionCount);
            this.examDuration = examDuration;
            this.examTitle = examTitle;
            this.questionCount = questionCount;
            Classroom classroom = classroomService.findByClassId(id);
            model.addAttribute("classroom", classroom);

            UserDTO currentUser = userService.getCurrentUser();
            model.addAttribute("userDTO", currentUser);

            model.addAttribute("examTitle", examTitle);

            model.addAttribute("questionDTO", new QuestionDTO(this.questionCount));

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "deo biet chuyen j da xay ra");
        }
        return Constant.PAGE_TEACHER_UPDATE_QUESTION;
    }

    @PostMapping(value = Constant.PAGE_TEACHER_CLASSROOM + "/{id}/exam/create/question")
    public String createExam(Model model,
            @PathVariable("id") String id,
            @Validated @ModelAttribute("questionDTO") QuestionDTO questionDTO,
            RedirectAttributes redirectAttributes) {
        try {
            Classroom classroom = classroomService.findByClassId(id);
            ExamDTO examDTO = new ExamDTO();
            examDTO.setExamTitle(examTitle);
            examDTO.setQuestionCount(questionCount);
            examDTO.setExamDuration(examDuration);

            List<Question> questions = new ArrayList<>();
            for (int i = 0; i < questionCount; i++) {
                String idquestion = UUID.randomUUID().toString();
                questionService.createQuestion(questionDTO, i, idquestion);
                questions.add(questionService.findByQuestionId(idquestion));
            }
            examService.createExam(examDTO, classroom, questions);

            redirectAttributes.addFlashAttribute("success", "Tạo đề thi mới thành công");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "deo biet chuyen j da xay ra");
        }
        return "redirect:/teacher/classroom/update/" + id;
    }

    @PostMapping(Constant.PAGE_TEACHER_CLASSROOM + "/exam/delete/{id}")
    public String deleteExam(@PathVariable("id") String examId, RedirectAttributes redirectAttributes) {
        // TODO: process POST request
        Exam exam = examService.findByExamId(examId);

        if (exam == null) {
            redirectAttributes.addFlashAttribute("error", "Đề thi không tồn tại.");
        }
        examService.deleteExamById(examId);
        redirectAttributes.addFlashAttribute("success", "Xóa đề thi thành công.");
        String classId = exam.getClassAssignedId().getClassId();
        return "redirect:/teacher/classroom/update/" + classId;
    }

    @GetMapping(value = Constant.STUDENT_SUBDIR + "/exam/{id}/prepare")
    public String showPrepareExamPage(@PathVariable("id") String examId, Model model) {

        try {
            UserDTO currentUser = userService.getCurrentUser();

            User student = userService.findByUsername(currentUser.getUsername());

            if (resultService.findByExamIdAndStudent(examId, student.getStudent()).size() != 0) {
                return Constant.ERROR_PAGE;
            }

            Exam exam = examService.findByExamId(examId);
            model.addAttribute("userDTO", currentUser);
            model.addAttribute("exam", exam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Constant.PAGE_STUDENT_PREPARE_EXAM;
    }

    @GetMapping(value = Constant.STUDENT_SUBDIR + "/exam/{id}/contest")
    public String showContestExamPage(@PathVariable("id") String examId, Model model) {
        try {
            UserDTO currentUser = userService.getCurrentUser();
            Exam exam = examService.findByExamId(examId);

            User student = userService.findByUsername(currentUser.getUsername());

            if (resultService.findByExamIdAndStudent(examId, student.getStudent()).size() != 0) {
                return Constant.ERROR_PAGE;
            }

            model.addAttribute("userDTO", currentUser);
            model.addAttribute("questionContent", exam.getQuestions());
            model.addAttribute("examId", exam.getExamId());
            String[] answers = new String[exam.getQuestionCount()];
            for (int i = 0; i < answers.length; i++) {
                answers[i] = "";
            }
            model.addAttribute("answerDTO", new AnswerDTO(exam.getQuestionCount()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Constant.PAGE_STUDENT_CONTEST;
    }

    @PostMapping(value = Constant.STUDENT_SUBDIR + "/exam/{id}/confirm")
    public String showResultPage(@PathVariable("id") String examId,
            @Validated @ModelAttribute("answerDTO") AnswerDTO answerDTO, Model model) {
        try {
            UserDTO currentUser = userService.getCurrentUser();
            Exam exam = examService.findByExamId(examId);


            User student = userService.findByUsername(currentUser.getUsername());

            if (resultService.findByExamIdAndStudent(examId, student.getStudent()).size() != 0) {
                return Constant.ERROR_PAGE;
            }

            double score = 0;
            int correctAnswerCount = 0;
            for (int i = 0; i < exam.getQuestionCount(); i++) {
                if (answerDTO.getUserAnswers()[i].equals(exam.getQuestions().get(i).getCorrectAnswer())) {
                    correctAnswerCount++;
                }
            }
            score = correctAnswerCount * 1.0 / exam.getQuestionCount() * 10;

            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setCorrectAnswerCount(correctAnswerCount);
            resultDTO.setExam(exam);
            resultDTO.setStudent(student.getStudent());
            resultDTO.setScore(score);
            resultService.createResult(resultDTO);
            model.addAttribute("userDTO", currentUser);
            // System.out.println(currentUser.getLastname());
            model.addAttribute("correctAnswerCount", correctAnswerCount);
            model.addAttribute("questionCount", exam.getQuestionCount());
            model.addAttribute("score", score);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Constant.PAGE_STUDENT_CONTEST_RESULT;
    }

}
