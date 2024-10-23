package com.web.ptitexam.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    private String teacherId;
    private String department;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @OneToMany(mappedBy = "teacher")
    private List<Classroom> classrooms;

    @Column(nullable = false)
    private String[] examIdList;

    public String[] getExamIdList() {
        return examIdList;
    }

    public void setExamIdList(String[] examIdList) {
        this.examIdList = examIdList;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
