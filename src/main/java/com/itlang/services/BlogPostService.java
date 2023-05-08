package com.itlang.services;

import com.itlang.models.BlogPost;
import com.itlang.models.Image;
import com.itlang.models.BlogPostSubtheme;
import com.itlang.models.BlogPostVideo;
import com.itlang.repositories.BlogPostImageRepository;
import com.itlang.repositories.BlogPostRepository;
import com.itlang.repositories.BlogPostSubthemeRepository;
import com.itlang.repositories.BlogPostVideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Vadym Hnatiuk
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final BlogPostSubthemeRepository subthemeRepository;
    private final BlogPostImageRepository imageRepository;
    private final BlogPostVideoRepository videoRepository;

    public List<BlogPost> list(){
        return blogPostRepository.findAllByOrderByIdDesc();

    }

    public void savePost(MultipartFile preview, MultipartFile[] file, BlogPost blogPost, BlogPostSubtheme subtheme, BlogPostVideo video) throws IOException {
        //images
        Image previewImage;
        if(preview.getSize()!= 0){
            previewImage = toImageEntity(preview);
            previewImage.setPreviewImage(true);
            blogPost.addImageToPost(previewImage);
        }

        Image image;
        if (file[0].getSize()!=0){
            for (MultipartFile multipartFile : file) {
                image = toImageEntity(multipartFile);
                blogPost.addImageToPost(image);
            }
        }

        //previewText
        String prText = blogPost.getBlogPostText();
        if (prText.length() <= 151){
            blogPost.setPreviewText(prText);
        }
        else {
            blogPost.setPreviewText(prText.substring(0,151));
        }

        blogPost.addSubthemeToPost(subtheme);
        if (!video.getBlogPostVideoLink().equals("")){
            blogPost.addVideoToPost(video);
        }

        BlogPost postFromDB = blogPostRepository.save(blogPost);
        postFromDB.setPreviewImageId(postFromDB.getImages().get(0).getId());
        blogPostRepository.save(blogPost);
    }
    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());

        return image;
    }

    public void deletePost(long id){
        blogPostRepository.deleteById(id);
    }

    public BlogPost getPostById(Long id) {
        BlogPost blogPost = new BlogPost();
        blogPost = blogPostRepository.findById(id).orElse(null);
        blogPost.addViews();
        blogPostRepository.save(blogPost);
        return  blogPost;
    }

    public void addSubtheme(BlogPostSubtheme subtheme, Long id){
        BlogPost blogPost = blogPostRepository.findBlogPostById(id);
        blogPost.addSubthemeToPost(subtheme);
        blogPostRepository.save(blogPost);
    }

    public void deleteSubtheme(Long id){
        subthemeRepository.deleteById(id);
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    public void addVideo(BlogPostVideo blogPostVideo, Long id) {
        BlogPost blogPost = blogPostRepository.findBlogPostById(id);
        blogPost.addVideoToPost(blogPostVideo);
        blogPostRepository.save(blogPost);
    }

    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }

    public void saveEditedPost(Long id, MultipartFile preview, MultipartFile[] multipartFiles, BlogPost blogPost) throws IOException {
        BlogPost blogPostToBeUpdated = blogPostRepository.findBlogPostById(id);

        // preview
        Image previewImage;
        if(preview.getSize()!= 0){
            imageRepository.deleteById(blogPostToBeUpdated.getPreviewImageId());
            previewImage = toImageEntity(preview);
            previewImage.setPreviewImage(true);
            blogPostToBeUpdated.addImageToPost(previewImage);
            imageRepository.save(previewImage);
            blogPostToBeUpdated.setPreviewImageId(previewImage.getId());
        }

        //post
        blogPostToBeUpdated.setBlogPostTitle(blogPost.getBlogPostTitle());
        blogPostToBeUpdated.setBlogPostText(blogPost.getBlogPostText());

        Image image;
        if (multipartFiles[0].getSize()!=0){
            for (MultipartFile multipartFile : multipartFiles) {
                image = toImageEntity(multipartFile);
                System.out.println(image.getName() + " +++ ");
                blogPostToBeUpdated.addImageToPost(image);
            }
        }

        String prText = blogPost.getBlogPostText();
        if (prText.length() <= 151){
            blogPostToBeUpdated.setPreviewText(prText);
        }
        else {
            blogPost.setPreviewText(prText.substring(0,151));
        }

        blogPostRepository.save(blogPostToBeUpdated);
    }

    public void editSubtheme(Long id, BlogPostSubtheme subtheme) {
        BlogPostSubtheme oldSubtheme = subthemeRepository.findBlogPostSubthemeById(id);
        oldSubtheme.setBlogPostSubthemeTitle(subtheme.getBlogPostSubthemeTitle());
        oldSubtheme.setBlogPostSubthemeText(subtheme.getBlogPostSubthemeText());

        subthemeRepository.save(oldSubtheme);
    }

    public void editVideo(Long id, BlogPostVideo video) {
        BlogPostVideo oldVideo = videoRepository.findBlogPostVideoById(id);
        oldVideo.setBlogPostVideoLink(video.getBlogPostVideoLink());

        videoRepository.save(oldVideo);
    }
}
