package com.web.ptitexam.entity;

import jakarta.persistence.*;


@Entity
@Table (name = "students")
public class Student {
    @Id
    private String studentId;
    private String major;
    private String className;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
