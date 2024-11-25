package com.web.ptitexam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "classrooms")
public class Classroom {

    @Id
    @Column(updatable = false, nullable = false)
    private String classId;

    @Column(nullable = false)
    private String className;

    @ManyToOne()
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToMany
    @JoinTable(name = "classroom_student", joinColumns = @JoinColumn(name = "class_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    private Instant classCreatedAt;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Instant getClassCreatedAt() {
        return classCreatedAt;
    }

    public void setClassCreatedAt(Instant classCreatedAt) {
        this.classCreatedAt = classCreatedAt;
    }

    // tự động set thời gian khi tạo mới
    @PrePersist
    protected void onCreate() {
        this.classCreatedAt = Instant.now();
    }
}
