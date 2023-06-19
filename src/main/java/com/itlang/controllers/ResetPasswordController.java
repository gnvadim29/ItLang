package com.itlang.controllers;

import com.itlang.models.Person;
import com.itlang.repositories.PeopleRepository;
import com.itlang.services.PersonService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ResetPasswordController {
    private final PersonService personService;
    private final PeopleRepository peopleRepository;


    @GetMapping("/auth/resetPassword")
    public String getResetPage(){
        return "auth/reset_password";
    }
    @PostMapping("/auth/resetPassword")
    public String resetPassword(@RequestParam(name = "email") String email, Model model, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {

        Person person = personService.getPersonByEmail(email);
        if (person == null){
            model.addAttribute("find", "Користувача не знайдено");
        } else {
            Thread registrationThread = new Thread(() -> {
                String siteUrl = getSiteURL(request);
                try {
                    personService.setResetToken(generateResetToken(), person, siteUrl);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            });
            registrationThread.start();
            model.addAttribute("find", "Лист з підтвержденням відправлено на пошту");
        }
        return "auth/reset_password";
    }

    @GetMapping("/reset")
    public String reset(@Param("token") String token, Model model){
        Person user = peopleRepository.findPersonByResetToken(token);

        if (user == null){
            return "auth/reset_error";
        } else {
            model.addAttribute("user", user);
            return "auth/new_password";
        }
    }
    @GetMapping("/auth/changePassword")
    public String changePassword(@RequestParam(name = "id") Long id,
                                 @RequestParam(name = "new") String newPassword,
                                 @RequestParam(name = "confirm_new") String confirm, Model model)
    {
        Person person = peopleRepository.findPersonById(id);

        if (newPassword.equals(confirm)){
            personService.changePassword(person, newPassword);
            return "redirect:/auth/login";
        } else {
            model.addAttribute("user", person);
            model.addAttribute("error", "Паролі не співпадають");
            return "auth/new_password";
        }
    }
    public String generateResetToken() {
        return UUID.randomUUID().toString();
    }
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
