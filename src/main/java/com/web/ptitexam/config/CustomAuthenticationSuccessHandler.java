package com.web.ptitexam.config;

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
        String redirectUrl = ""; // Default redirect URL

        // Log for debugging
        System.out.println("Authentication Success for user: " + authentication.getName());
        System.out.println("Authorities: " + authentication.getAuthorities());

        // Redirect based on the role
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TEACHER"))) {
            redirectUrl = "/ptit-exam/teacher/class"; // Redirect teachers
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"))) {
            redirectUrl = "/ptit-exam/student/exam";  // Redirect students
        } else {
            // Fallback URL in case no role matches
            redirectUrl = "/ptit-exam/error";
        }

        // Check if redirectUrl is set and send redirect
        if (!redirectUrl.isEmpty()) {
            response.sendRedirect(redirectUrl);
        } else {
            throw new IllegalStateException("Redirect URL is not set.");
        }
    }
}
