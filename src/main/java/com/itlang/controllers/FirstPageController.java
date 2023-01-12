package com.itlang.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Vadym Hnatiuk
 */
@Controller
public class FirstPageController {

    @GetMapping("/")
    public String firstPage(){
        return "index";
    }

}
