package com.itlang.controllers;

import com.itlang.models.Person;
import com.itlang.services.RegistrationService;
import com.itlang.util.PersonValidator;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * @author Vadym Hnatiuk
 */
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;

    @GetMapping("/auth/login")
    public String loginPage(@ModelAttribute ("person") Person person){
        return "authorisation";
    }

    @PostMapping("/auth/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person,
                                      BindingResult bindingResult, Model model,
                                      HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("hasErrors", true);
            return "/authorisation";
        }

        registrationService.register(person, getSiteURL(request));
        model.addAttribute("verify", true);
        return "/authorisation";
    }
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        System.out.println("++++++++++++++++++++++++++++++++++");
        if (registrationService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

}