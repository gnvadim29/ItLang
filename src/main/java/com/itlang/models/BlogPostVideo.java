package com.itlang.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vadym Hnatiuk
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BlogPostVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String blogPostVideoLink;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private BlogPost blogPost;
}
