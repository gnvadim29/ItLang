package com.itlang.controllers.admin;

import com.itlang.models.Person;
import com.itlang.repositories.PeopleRepository;
import com.itlang.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminUsersController {
    private final PeopleRepository peopleRepository;
    private final PersonService personService;

    @GetMapping("/users")
    public String getUsers(Model model){
        model.addAttribute("users", peopleRepository.findAll());
        return "admin/admin_users";
    }
    @GetMapping("/users/find")
    public String getUser(@RequestParam(name = "email") String email, Model model){
        List<Person> users = new ArrayList<>();
        Person person = peopleRepository.findPersonByEmail(email);
        if (person != null){
            users.add(person);
            model.addAttribute("users", users);
        } else {
            model.addAttribute("users", null);
        }
        return "admin/admin_users";
    }

    @GetMapping("/users/{id}/promote")
    public String promoteToAdmin(@PathVariable(name = "id") Long id){
        Person person = peopleRepository.findPersonById(id);
        person.setRole("ROLE_ADMIN");
        peopleRepository.save(person);
        return "redirect:/admin/users";
    }
    @GetMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable(name = "id") Long id){
        personService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
