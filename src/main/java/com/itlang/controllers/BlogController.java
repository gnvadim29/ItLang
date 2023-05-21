package com.itlang.controllers;

import com.itlang.models.BlogPost;
import com.itlang.models.BlogPostSubtheme;
import com.itlang.models.BlogPostVideo;
import com.itlang.repositories.BlogPostSubthemeRepository;
import com.itlang.repositories.BlogPostVideoRepository;
import com.itlang.services.BlogPostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Vadym Hnatiuk
 */
@Controller
public class BlogController {

    private final BlogPostService blogPostService;
    public BlogController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping("/blog")
    public String blogPage(Model model) {
        model.addAttribute("blog_post", blogPostService.list());
        return "blog/blog";
    }
    @GetMapping("/blog/post/{id}")
    public String showPost(@PathVariable Long id, Model model){
        BlogPost post = blogPostService.getPostById(id);
        post.setViewsCount(post.getViewsCount() +1 );
        model.addAttribute("blog_post", post);
        model.addAttribute("subthemes", post.getSubthemes());
        model.addAttribute("videos", post.getVideos());
        model.addAttribute("images", post.otherImages());
        return "blog/post-info";
    }
}