package com.itlang.controllers.admin;

import com.itlang.models.BlogPost;
import com.itlang.models.BlogPostSubtheme;
import com.itlang.models.BlogPostVideo;
import com.itlang.repositories.BlogPostRepository;
import com.itlang.services.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final BlogPostService blogPostService;
    @GetMapping("/admin")
    public String adminPage(){
        return "admin/admin";
    }

//    BLOG ////////////////////////////////////
    @GetMapping("/admin/blog")
    public String adminBlog(Model model){
        model.addAttribute("blog_post", blogPostService.list());
        model.addAttribute("post", new BlogPost());
        model.addAttribute("subtheme", new BlogPostSubtheme());
        model.addAttribute("video", new BlogPostVideo());
        return "admin/admin_blog";
    }

}
