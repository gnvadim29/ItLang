package com.itlang.services;

import com.itlang.models.Person;
import com.itlang.models.UserProgress;
import com.itlang.repositories.PeopleRepository;
import com.itlang.repositories.UserProgressRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * @author Vadym Hnatiuk
 */
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProgressRepository progressRepository;
    private final JavaMailSender mailSender;

    public void register(Person person, String siteUrl) throws MessagingException, UnsupportedEncodingException {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");

        String randomCode = RandomString.make(64);
        person.setVerificationCode(randomCode);
        person.setEnabled(false);
        peopleRepository.save(person);

        UserProgress progress = new UserProgress();
        progress.setPerson(person);
        progressRepository.save(progress);
        sendVerificationEmail(person, siteUrl);
    }

    void sendVerificationEmail(Person person, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = person.getEmail();
        String fromAddress = "itLangNEMK@gmail.com";
        String senderName = "ItLang";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "ItLang team.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", person.getName());
        String verifyURL = siteURL + "/verify?code=" + person.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }
    public boolean verify(String verificationCode) {
        Person user = peopleRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            peopleRepository.save(user);
            return true;

        }



    }

}
