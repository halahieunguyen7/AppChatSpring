package com.example.ChatApp.Infrastructure.Mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String token) {
        String subject = "Xác thực tài khoản ChatApp";
        String verificationUrl = "http://chat.localhost/auth/verify?token=" + token;

        String content = """
            <p>Xin chào,</p>
            <p>Bạn vừa đăng ký tài khoản trên ChatApp.</p>
            <p>Vui lòng nhấn vào link bên dưới để xác thực tài khoản:</p>
            <p><a href="%s">Xác thực tài khoản</a></p>
            <br>
            <p>Nếu bạn không đăng ký, hãy bỏ qua email này.</p>
            """.formatted(verificationUrl);

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("appchat@com.vn");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true); // true = HTML
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
