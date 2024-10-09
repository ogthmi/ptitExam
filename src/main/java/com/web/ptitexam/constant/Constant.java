package com.web.ptitexam.constant;

public class Constant {

    // Roles
    public static final String ROLE_STUDENT = "STUDENT";
    public static final String ROLE_TEACHER = "TEACHER";
    public static final String ROLE_ADMIN = "ADMINISTRATOR";

    // Absolute URL
    public static final String MAIN_DIR = "/ptit-exam";

    //Redirect
    public static final String REDIRECT = "redirect:/";

    // Shared pages
    public static final String PAGE_LOGIN = "login";
    public static final String PAGE_REGISTER = "register";
    public static final String PAGE_USER_INFO = "user-info";

    // Admin's page
    public static final String ADMIN_SUBDIR = "admin";
    public static final String PAGE_ADMIN_DASHBOARD = ADMIN_SUBDIR + "/dashboard";

    // Teacher's page
    public static final String TEACHER_SUBDIR = "teacher";
    public static final String PAGE_TEACHER_CLASSROOM = TEACHER_SUBDIR + "/classroom";

    // Student's page
    public static final String STUDENT_SUBDIR = "student";
    public static final String PAGE_STUDENT_CLASSROOM = STUDENT_SUBDIR + "/classroon";
}
