package org.wyona.webapp.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.wyona.webapp.mail.EmailSender;
import org.wyona.webapp.models.Email;
import org.wyona.webapp.models.Greeting;

import javax.mail.MessagingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MailerService {
    private final EmailSender emailSender;

    @Autowired
    public MailerService(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    /**
     * @param recipient E-Mail address of recipient
     * @param greeting Greeting which will be sent to recipient
     */
    public Greeting sendEmail(String recipient, Greeting greeting) throws MessagingException {
        // TODO: Check whether format of greeting body is HTML
        sendEmail(new Email(recipient, greeting.getGreeting(), greeting.getGreeting(), false));

        return greeting;
    }

    /**
     * Send email greeting
     * @param email Email address
     * @param subject Email subject
     * @param text Email body text
     * @param isHTMLMessage True when text is HTML and false when text is plain
     */
    public void sendEmail(String email, String subject, String text, boolean isHTMLMessage) throws MessagingException {
        sendEmail(new Email(email, subject, text, isHTMLMessage));
    }

    /**
     * @param email Email object, containing email address, subject and body text
     */
    public void sendEmail(Email email) throws MessagingException {
        emailSender.sendEmailGreeting(email.getEmail(), email.getSubject(), email.getText(), email.isHTMLMessage(), email.getAttachment());

        log.info("Email sent to {}", email.getEmail());
    }
}
