package com.itlang.models.course;

import com.itlang.models.BlogPostSubtheme;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 30, message = "Title should be between 1 and 30 characters")
    private String title;

    private String courseUrl;

    private int numberOfQuestions;

    @Column(columnDefinition = "boolean default false")
    private boolean active;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "course")
    private List<Level> levels = new ArrayList<>();

    public void addLevel(Level level){
        level.setCourse(this);
        levels.add(level);
    }
}
