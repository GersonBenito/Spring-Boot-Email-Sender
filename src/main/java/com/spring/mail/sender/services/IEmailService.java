package com.spring.mail.sender.services;

import java.io.File;

public interface IEmailService {
    public void sendEmail(String[] toUser, String subject, String message);

    public void sendEmailWithFile(String[] toUser, String subject, String message, File file);
}
