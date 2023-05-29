package com.itlang.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Vadym Hnatiuk
 */
@Controller
public class ItLangController {

    @GetMapping("/")
    public String index(){
        System.out.println("+++");
        return "index";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/login?logout";
    }

}
