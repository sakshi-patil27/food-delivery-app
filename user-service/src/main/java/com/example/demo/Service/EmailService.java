package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@gmail.com"); // same as spring.mail.username
        message.setTo(toEmail);
        message.setSubject("Grabobite OTP Verification Code");

        String mailBody = """
                Hello 👋,

                Thank you for registering with Grabobite – your go-to food delivery app!

                🔐 Your One-Time Password (OTP) is: %s

                This OTP is valid for only 5 minutes. Please do not share it with anyone.

                If you did not initiate this request, please ignore this email.

                🍽️ Happy ordering,
                – Team Grabobite
                """.formatted(otp);

        message.setText(mailBody);
        mailSender.send(message);
    }

}

