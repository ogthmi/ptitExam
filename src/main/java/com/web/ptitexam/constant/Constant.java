package com.web.ptitexam.constant;

import javax.swing.plaf.PanelUI;

public class Constant {

    // Gender
    // Gender
    public static final String GENDER_MALE = "MALE";
    public static final String GENDER_FEMALE = "FEMALE";

    // Roles
    public static final String ROLE_STUDENT = "STUDENT";
    public static final String ROLE_TEACHER = "TEACHER";
    public static final String ROLE_ADMIN = "ADMIN";

    // Absolute URL
    public static final String MAIN_DIR = "/ptit-exam";

    // Redirect
    // Redirect
    public static final String REDIRECT = "redirect:/";

    // Shared pages
    public static final String PAGE_LOGIN = "login";
    public static final String PAGE_REGISTER = "register";
    public static final String PAGE_USER_PROFILE = "user-info";

    // Admin's page
    public static final String ADMIN_SUBDIR = "admin";
    public static final String PAGE_ADMIN_DASHBOARD = ADMIN_SUBDIR + "/dashboard";

    // Teacher's page
    public static final String TEACHER_SUBDIR = "teacher";
    public static final String PAGE_TEACHER_CLASSROOM = TEACHER_SUBDIR + "/classroom";
    public static final String PAGE_TEACHER_UPDATE_CLASSROOM = TEACHER_SUBDIR + "/update.classroom";
    public static final String PAGE_TEACHER_UPDATE_EXAM = TEACHER_SUBDIR + "/update.exam";
    public static final String PAGE_TEACHER_UPDATE_QUESTION = TEACHER_SUBDIR + "/update.exam.question";

    // Student's page
    public static final String STUDENT_SUBDIR = "student";
    public static final String PAGE_STUDENT_CLASSROOM = STUDENT_SUBDIR + "/classroom";

    public static final String PAGE_STUDENT_VIEW_CLASSROOM = STUDENT_SUBDIR + "/view.classroom";
    public static final String PAGE_STUDENT_PREPARE_EXAM = STUDENT_SUBDIR + "/view.exam";
    public static final String PAGE_STUDENT_CONTEST = STUDENT_SUBDIR + "/view.exam.contest";
    public static final String PAGE_STUDENT_CONTEST_RESULT = STUDENT_SUBDIR + "/view.exam.result";
}
