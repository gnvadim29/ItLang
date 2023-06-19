package com.itlang.services;

import com.itlang.models.Image;
import com.itlang.models.Person;
import com.itlang.models.UserProgress;
import com.itlang.models.course.UserQuestions;
import com.itlang.repositories.BlogPostImageRepository;
import com.itlang.repositories.PeopleRepository;
import com.itlang.repositories.UserProgressRepository;
import com.itlang.repositories.course.UserQuestionsRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PeopleRepository peopleRepository;
    private final UserQuestionsRepository userQuestionsRepository;
    private final UserProgressRepository userProgressRepository;
    private final BlogPostImageRepository imageRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public void deleteUser(Long id){
        Person person = peopleRepository.findPersonById(id);

        UserProgress userProgress = userProgressRepository.findUserProgressById(person.getUserProgress().getId());
        userProgressRepository.delete(userProgress);

        List<UserQuestions> userQuestions = userQuestionsRepository.findUserQuestionsByPersonId(id);
        for (int i = 0; i < userQuestions.size(); i++){
            userQuestionsRepository.delete(userQuestions.get(i));
        }

        if(person.getUserIconId()!=null){
            Image image = imageRepository.findImageById(person.getUserIconId());
            imageRepository.delete(image);
        }

        peopleRepository.delete(person);
    }

    public void setLastActivity(Person person) {
        LocalDate time = LocalDate.now();
        person.setLastActivity(time);
        peopleRepository.save(person);
    }

    public void deleteInactive() {
        List<Person> people = peopleRepository.findInactive();
        for (int i = 0; i < people.size(); i++){
            deleteUser(people.get(i).getId());
        }
        List<Person> people1 = peopleRepository.findDisabled();
        for (int i = 0; i < people1.size(); i++){
            peopleRepository.delete(people1.get(i));
        }
    }

    public Person getPersonByEmail(String email){
        return peopleRepository.findPersonByEmail(email);
    }

    public void setResetToken(String resetToken, Person person, String siteUrl) throws MessagingException, UnsupportedEncodingException {
        person.setResetToken(resetToken);
        peopleRepository.save(person);
        sendResetEmail(person, siteUrl);
    }
    void sendResetEmail(Person person, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = person.getEmail();
        String fromAddress = "itLangNEMK@gmail.com";
        String senderName = "ItLang";
        String subject = "Please confirm password reset";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to confirm password reset:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">CONFIRM</a></h3>"
                + "Thank you,<br>"
                + "ItLang team.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", person.getName());
        String verifyURL = siteURL + "/reset?token=" + person.getResetToken();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }

    public void changePassword(Person person, String newPassword) {
        person.setPassword(passwordEncoder.encode(newPassword));
        person.setResetToken(null);
        peopleRepository.save(person);
    }
}
