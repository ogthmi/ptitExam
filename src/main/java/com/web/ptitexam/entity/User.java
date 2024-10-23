package com.web.ptitexam.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.ptitexam.dto.ClassroomDTO;
import com.web.ptitexam.dto.StudentDTO;

@Entity
@Table(name = "users")
public class User {
    @Id

    @Column(updatable = false, nullable = false)
    private String userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Teacher teacher;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore()
    private Student student;

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentDTO convertToStudentDTO() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(this.student.getStudentId());
        studentDTO.setName(this.firstname + " " + this.lastname);
        studentDTO.setMajor(this.student.getMajor());
        studentDTO.setClassName(this.student.getClassName());

        // Convert list of Classroom to list of ClassroomDTO
        List<ClassroomDTO> tmp = this.student.getClassrooms().stream().map(classroom -> {
            ClassroomDTO classroomDTO = new ClassroomDTO();
            classroomDTO.setClassName(classroom.getClassName());
            return classroomDTO;
        }).collect(Collectors.toList());

        studentDTO.setClassrooms(tmp);
        return studentDTO;
    }

}
