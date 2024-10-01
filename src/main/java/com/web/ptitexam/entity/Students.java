package com.web.ptitexam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table (name = "students")
public class Students extends User{
    @Column (name = "class_name")
    private String className;
}
