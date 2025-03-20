package io.cafekiosk.spring.api.mail.service;

import io.cafekiosk.spring.client.mail.MailSendClient;
import io.cafekiosk.spring.domain.hisotry.entity.mail.MailSendHistory;
import io.cafekiosk.spring.domain.hisotry.repository.MailSendHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailService {

    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;

    public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {

        boolean result = mailSendClient.sendEmail(fromEmail, toEmail, subject, content);

        if (result) {
            mailSendHistoryRepository.save(MailSendHistory.builder()
                    .fromEmail(fromEmail)
                    .toEmail(toEmail)
                    .subject(subject)
                    .content(content)
                    .build()
            );
            return true;
        }
        return false;
    }
}
