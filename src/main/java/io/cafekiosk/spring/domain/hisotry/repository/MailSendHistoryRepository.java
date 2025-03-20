package io.cafekiosk.spring.domain.hisotry.repository;

import io.cafekiosk.spring.domain.hisotry.entity.mail.MailSendHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailSendHistoryRepository extends JpaRepository<MailSendHistory, Long> {
}
