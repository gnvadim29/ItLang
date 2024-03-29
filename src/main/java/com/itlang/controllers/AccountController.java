package com.itlang.controllers;

import com.itlang.models.Person;
import com.itlang.models.UserProgress;
import com.itlang.repositories.PeopleRepository;
//import com.itlang.repositories.UserProgressRepository;
import com.itlang.security.PersonDetails;
import com.itlang.services.AccountService;
import com.itlang.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/*
 * @author Vadym Hnatiuk
 */
@Controller
@RequiredArgsConstructor
public class AccountController {

    private final PeopleRepository peopleRepository;
    private final AccountService accountService;
    private final PersonService personService;
    @GetMapping("/myaccount")
    public String accountPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = peopleRepository.findPersonByEmail(authentication.getName());
        personService.setLastActivity(person);
        model.addAttribute("user", person);
        return "myaccount";
    }

    @PostMapping("/myaccount/changeIcon")
    public String changeIcon(@RequestParam(name = "userIcon") MultipartFile userIcon) throws IOException {
        System.out.println(userIcon.getName());
        accountService.changeIcon(userIcon);
        return "redirect:/myaccount";
    }
    @PostMapping("/myaccount/changeInfo")
    public String changeInfo(@ModelAttribute(name = "user") Person person){
        accountService.changeInfo(person);
        System.out.println(person.getName() + " " + person.getSurname() + " " + person.getGroup() + " " + person.getEmail());
        return "redirect:/myaccount";
    }
    @GetMapping("/myaccount/{id}/delete")
    public String deleteAccount(@PathVariable(name = "id") Long id){
        personService.deleteUser(id);
        return "redirect:/logout";
    }
}
