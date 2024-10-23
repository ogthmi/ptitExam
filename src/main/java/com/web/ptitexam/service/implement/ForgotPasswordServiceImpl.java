package com.web.ptitexam.service.implement;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.web.ptitexam.entity.ForgotPassword;
import com.web.ptitexam.repository.ForgotPasswordRepository;
import com.web.ptitexam.service.ForgotPasswordService;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    private final ForgotPasswordRepository forgotPasswordRepository;

    public ForgotPasswordServiceImpl(ForgotPasswordRepository forgotPasswordRepository) {
        this.forgotPasswordRepository = forgotPasswordRepository;
    }

    @Override
    public void createToken(String email, String token) {
        ForgotPassword forgotPassword = new ForgotPassword();

        forgotPassword.setId(UUID.randomUUID().toString());
        forgotPassword.setEmail(email);
        forgotPassword.setToken(token);

        forgotPasswordRepository.save(forgotPassword);
    }

    @Override
    public ForgotPassword findByEmail(String email) {
        return forgotPasswordRepository.findByEmail(email);
    }

    @Override
    public ForgotPassword findByToken(String token) {
        return forgotPasswordRepository.findByToken(token);
    }

    @Override
    public void save(ForgotPassword forgotPassword) {
        forgotPasswordRepository.save(forgotPassword);
    }

    @Override
    public void delete(ForgotPassword forgotPassword) {
        forgotPasswordRepository.delete(forgotPassword);
    }
}
