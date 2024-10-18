package com.web.ptitexam.service;

public interface EmailService {
    void sendEmailSync(String to, String subject, String content, boolean isMultipart, boolean isHtml);

    void sendEmailFromTemplateSync(String to, String subject, String templateName, String text);
}
