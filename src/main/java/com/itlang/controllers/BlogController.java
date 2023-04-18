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
@RequestMapping("/blog")
public class BlogController {

    private final BlogPostService blogPostService;
    private final BlogPostSubthemeRepository subthemeRepository;
    private final BlogPostVideoRepository videoRepository;
    public BlogController(BlogPostService blogPostService, BlogPostSubthemeRepository subthemeRepository, BlogPostVideoRepository videoRepository) {
        this.blogPostService = blogPostService;
        this.subthemeRepository = subthemeRepository;
        this.videoRepository = videoRepository;
    }

    @GetMapping()
    public String blogPage(Model model){
        model.addAttribute("blog_post", blogPostService.list());
        return "blog/blog";
    }

    // POST ****************************************************************

    @GetMapping("/post/{id}")
    public String showPost(@PathVariable Long id, Model model){
        BlogPost post = blogPostService.getPostById(id);
        post.setViewsCount(post.getViewsCount() +1 );
        model.addAttribute("blog_post", post);
        model.addAttribute("subthemes", post.getSubthemes());
        model.addAttribute("videos", post.getVideos());
        model.addAttribute("images", post.otherImages());
        return "blog/post-info";
    }

    @GetMapping("/post/add")
    public String addPostPage(Model model){
        model.addAttribute("post", new BlogPost());
        model.addAttribute("subtheme", new BlogPostSubtheme());
        model.addAttribute("video", new BlogPostVideo());
        return "blog/blog_post-add";
    }

    @PostMapping("/post/add")
    public String addPost(@RequestParam(name = "previewImage") MultipartFile preview,
                          @RequestParam("files") MultipartFile[] multipartFiles,
                          @ModelAttribute(name = "post") BlogPost blogPost,
                          @ModelAttribute(name = "subtheme") BlogPostSubtheme subtheme,
                          @ModelAttribute(name = "video") BlogPostVideo video) throws IOException {
        blogPostService.savePost(preview, multipartFiles, blogPost, subtheme, video);
        System.out.println("++++");
        return "redirect:/blog";
    }

    @GetMapping("/post/{id}/edit")
    public String editPost(Model model, @PathVariable Long id){
        BlogPost blogPost = blogPostService.getPostById(id);
        model.addAttribute("post", blogPost);
        model.addAttribute("subtheme", blogPost.getSubthemes());
        model.addAttribute("images", blogPost.otherImages());
        model.addAttribute("videos", blogPost.getVideos());

        return "blog/blog_post-edit";
    }

    @PostMapping("/post/{id}/edit")
    public String saveEditedPost(@PathVariable Long id,
                                 @RequestParam(name = "previewImage", required = false) MultipartFile preview,
                                 @RequestParam(name = "files", required = false) MultipartFile[] multipartFiles,
                                 @ModelAttribute(name = "post") BlogPost blogPost
                                 ) throws IOException {

        blogPostService.saveEditedPost(id, preview, multipartFiles, blogPost);
        return "redirect:/blog";
    }

    @GetMapping("/post/{id}/delete")
    public String deletePost(@PathVariable Long id){
        blogPostService.deletePost(id);
        return "redirect:/blog";
    }

    // SUBTHEME *******************************************************

    @GetMapping("/post/{id}/edit/add-subtheme")
    public String addSubtheme(Model model, @PathVariable String id){
        model.addAttribute("subtheme", new BlogPostSubtheme());
        return "blog/add-subtheme";
    }

    @PostMapping("/post/{id}/edit/add-subtheme")
    public String saveNewSubtheme(@ModelAttribute("subtheme") BlogPostSubtheme subtheme, @PathVariable Long id) {
        BlogPostSubtheme blogPostSubtheme = new BlogPostSubtheme();
        blogPostSubtheme.setBlogPostSubthemeTitle(subtheme.getBlogPostSubthemeTitle());
        blogPostSubtheme.setBlogPostSubthemeText(subtheme.getBlogPostSubthemeText());

        blogPostService.addSubtheme(blogPostSubtheme, id);
        return "redirect:/blog/post/{id}/edit";
    }

    @GetMapping("/post/{id}/edit/subtheme/{sid}/delete")
    public String deleteSubtheme(@PathVariable Long id, @PathVariable Long sid){
        blogPostService.deleteSubtheme(sid);
        return "redirect:/blog/post/{id}/edit";

    }

    @GetMapping("/post/{id}/edit/subtheme/{sid}/edit")
    public String editSubtheme(Model model, @PathVariable ("sid") Long id){
        BlogPostSubtheme subtheme = subthemeRepository.findBlogPostSubthemeById(id);
        model.addAttribute("subtheme", subtheme);
        return "blog/edit-subtheme";
    }

    @PostMapping("/post/{id}/edit/subtheme/{sid}/edit-subtheme")
    public String saveSubtheme(@PathVariable("sid") Long id, @ModelAttribute("subtheme") BlogPostSubtheme subtheme){
        blogPostService.editSubtheme(id, subtheme);
        return "redirect:/blog/post/{id}/edit";
    }

    // IMAGE *******************************************************

    @GetMapping("/post/{id}/edit/image/{sid}/delete")
    public String deleteImage(@PathVariable Long id, @PathVariable Long sid){
        blogPostService.deleteImage(sid);
        return "redirect:/blog/post/{id}/edit";
    }

    // VIDEO *******************************************************

    @GetMapping("/post/{id}/edit/add-video")
    public String addVideo(Model model, @PathVariable String id){
        model.addAttribute("video", new BlogPostVideo());
        return "blog/add-video";
    }

    @PostMapping("/post/{id}/edit/add-video")
    public String saveNewVideo(@ModelAttribute("video") BlogPostVideo video, @PathVariable Long id) {
        BlogPostVideo blogPostVideo = new BlogPostVideo();
        blogPostVideo.setBlogPostVideoLink(video.getBlogPostVideoLink());

        blogPostService.addVideo(blogPostVideo, id);
        return "redirect:/blog/post/{id}/edit";
    }

    @GetMapping("/post/{id}/edit/video/{sid}/delete")
    public String deleteVideo(@PathVariable Long id, @PathVariable Long sid){
        blogPostService.deleteVideo(sid);
        return "redirect:/blog/post/{id}/edit";
    }
    @GetMapping("/post/{id}/edit/video/{sid}/edit")
    public String editVideo(Model model, @PathVariable ("sid") Long id){
        BlogPostVideo video = videoRepository.findBlogPostVideoById(id);
        model.addAttribute("video", video);
        return "blog/edit-video";
    }

    @PostMapping("/post/{id}/edit/video/{sid}/edit-video")
    public String saveVideo(@PathVariable("sid") Long id, @ModelAttribute("video") BlogPostVideo video){
        System.out.println(video.getBlogPostVideoLink());
        blogPostService.editVideo(id, video);
        return "redirect:/blog/post/{id}/edit";
    }

}
