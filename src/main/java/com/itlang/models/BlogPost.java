package com.itlang.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vadym Hnatiuk
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class BlogPost {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String blogPostTitle;
    @Column(columnDefinition = "text")
    private String blogPostText;
    private int viewsCount;
    private LocalDate dateOfCreation;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "blogPost")
    private List<Image> images = new ArrayList<>();
    private Long previewImageId;

    public List<Image> otherImages(){
        List<Image> othImg = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            if (!images.get(i).isPreviewImage()){
                othImg.add(images.get(i));
            }
        }
        return othImg;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "blogPost")
    private List<BlogPostSubtheme> subthemes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "blogPost")
    private List<BlogPostVideo> videos  = new ArrayList<>();


    public void addViews(){
        viewsCount++;
    }


    @PrePersist
    private void init(){
        dateOfCreation = LocalDate.now();
    }

    public void addImageToPost(Image image){
        image.setBlogPost(this);
        images.add(image);
    }
    public void addSubthemeToPost(BlogPostSubtheme subtheme){
        subtheme.setBlogPost(this);
        subthemes.add(subtheme);
    }

    public void addVideoToPost(BlogPostVideo video){
        video.setBlogPost(this);
        videos.add(video);
    }

    private String previewText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBlogPostTitle() {
        return blogPostTitle;
    }

    public void setBlogPostTitle(String blogPostTitle) {
        this.blogPostTitle = blogPostTitle;
    }

    public String getBlogPostText() {
        return blogPostText;
    }

    public void setBlogPostText(String blogPostText) {
        this.blogPostText = blogPostText;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Long getPreviewImageId() {
        return previewImageId;
    }

    public void setPreviewImageId(Long previewImageId) {
        this.previewImageId = previewImageId;
    }

    public List<BlogPostSubtheme> getSubthemes() {
        return subthemes;
    }

    public void setSubthemes(List<BlogPostSubtheme> subthemes) {
        this.subthemes = subthemes;
    }

    public List<BlogPostVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<BlogPostVideo> videos) {
        this.videos = videos;
    }

    public String getPreviewText() {
        return previewText;
    }

    public void setPreviewText(String previewText) {
        this.previewText = previewText;
    }

}
