package com.itlang.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author Vadym Hnatiuk
 */
@Data
@AllArgsConstructor
@Entity
public class BlogPostSubtheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String blogPostSubthemeTitle;
    @Column(columnDefinition = "text")
    private String blogPostSubthemeText;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private BlogPost blogPost;

    public BlogPostSubtheme(){

    }
    public BlogPostSubtheme(String title, String text){
        this.blogPostSubthemeTitle = title;
        this.blogPostSubthemeText = text;
    }

}
