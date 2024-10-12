package com.web.ptitexam.security;

import com.web.ptitexam.constant.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        String redirectUrl;

        // Log for debugging
        System.out.println("Authentication Success for user: " + authentication.getName());
        System.out.println("Authorities: " + authentication.getAuthorities());

        // Redirect based on the role
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + Constant.ROLE_TEACHER))) {
            redirectUrl = Constant.MAIN_DIR + "/" + Constant.PAGE_TEACHER_CLASSROOM;
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + Constant.ROLE_STUDENT))) {
            redirectUrl = Constant.MAIN_DIR + "/" + Constant.PAGE_STUDENT_CLASSROOM;
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_"+Constant.ROLE_ADMIN))) {
            redirectUrl = Constant.MAIN_DIR + "/" + Constant.PAGE_ADMIN_DASHBOARD;
        } else {
            redirectUrl = Constant.MAIN_DIR + "/error";
        }

        // Check if redirectUrl is set and send redirect
        if (!redirectUrl.isEmpty()) {
            response.sendRedirect(redirectUrl);
        } else {
            throw new IllegalStateException("Redirect URL is not set.");
        }
    }
}
