package com.web.ptitexam.security;

import com.web.ptitexam.constant.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {
        System.out.println("Authentication failed.");
        request.getSession().setAttribute("error", "Tên đăng nhập hoặc mật khẩu không chính xác.");
        response.sendRedirect(Constant.MAIN_DIR + "/" + Constant.PAGE_LOGIN);
    }
}
