package com.itlang.models;

import com.itlang.models.course.Answer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vadym Hnatiuk
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String originalFileName;
    private Long size;
    private String contentType;
    private boolean isPreviewImage;
    @Column(columnDefinition = "longblob") //columnDefinition = LONGBLOB
    private byte[] bytes;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private BlogPost blogPost;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Answer answer;

}
