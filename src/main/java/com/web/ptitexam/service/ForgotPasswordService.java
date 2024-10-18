package com.web.ptitexam.service;

import com.web.ptitexam.entity.ForgotPassword;

public interface ForgotPasswordService {
    void createToken(String email, String token);

    ForgotPassword findByEmail(String email);

    ForgotPassword findByToken(String token);

    void save(ForgotPassword forgotPassword);

    void delete(ForgotPassword forgotPassword);
}
