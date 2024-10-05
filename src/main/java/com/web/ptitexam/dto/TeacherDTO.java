package com.web.ptitexam.dto;

public class TeacherDTO extends UserDTO{
    private String teacherId;
    private String department;

    public TeacherDTO(){
        super();
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
}
