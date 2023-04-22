package com.itlang.controllers;

import com.itlang.models.Person;
import com.itlang.models.UserProgress;
import com.itlang.repositories.PeopleRepository;
//import com.itlang.repositories.UserProgressRepository;
import com.itlang.security.PersonDetails;
import com.itlang.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/*
 * @author Vadym Hnatiuk
 */
@Controller
@RequiredArgsConstructor
public class AccountController {

    private final PeopleRepository peopleRepository;
//    private final UserProgressRepository progressRepository;
    private final AccountService accountService;

    @GetMapping("/myaccount")
    public String accountPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = peopleRepository.findPersonByEmail(authentication.getName());
        model.addAttribute("user", person);
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        UserProgress userProgress = progressRepository.findUserProgressByUserId(accountService.getUserId(email));
//        model.addAttribute("progress", userProgress);
        return "myaccount";
    }
    @GetMapping("/myaccount/changeIcon")
    public String changeIcon(){
        return "account_change";
    }

    @PostMapping("/myaccount/changeIcon")
    public String changeIcon(@RequestParam(name = "userIcon") MultipartFile userIcon) throws IOException {
        accountService.changeIcon(userIcon);
        return "redirect:/myaccount";
    }
}
