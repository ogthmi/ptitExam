package com.web.ptitexam.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "exam")
public class Exam {
    @Id
    @Column(nullable = false)
    private String examId;

    @Column(nullable = false)
    private String examTitle;

    @ManyToOne()
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToMany
    @JoinTable(name = "accessed_exam_student", joinColumns = @JoinColumn(name = "class_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> accessedExamStudents;

    @Column(nullable = false)
    private int examDuration;

    @Column(nullable = false)
    private int questionNumber;

    @Column(nullable = false)
    private int classAssigned;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    private Instant examCreatedAt;

    /// TODO: Get Question List (when)
    @Column(nullable = true)
    private ArrayList<String> questionIdList;

    public List<Student> getAccessedExamStudents() {
        return accessedExamStudents;
    }

    public void setAccessedExamStudents(List<Student> accessedExamStudents) {
        this.accessedExamStudents = accessedExamStudents;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Instant getExamCreatedAt() {
        return examCreatedAt;
    }

    public void setExamCreatedAt(Instant examCreatedAt) {
        this.examCreatedAt = examCreatedAt;
    }
    /// TODO: Get Question List
    // public ArrayList<String> getQuestionIdList() {
    // return questionIdList;
    // }

    public void setQuestionIdList(ArrayList<String> questionIdList) {
        this.questionIdList = questionIdList;
    }

    public Exam() {
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public int getExamDuration() {
        return examDuration;
    }

    public void setExamDuration(int examDuration) {
        this.examDuration = examDuration;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getClassAssigned() {
        return classAssigned;
    }

    public void setClassAssigned(int classAssigned) {
        this.classAssigned = classAssigned;
    }

    @PrePersist
    protected void onCreate() {
        this.examCreatedAt = Instant.now();
    }
}
