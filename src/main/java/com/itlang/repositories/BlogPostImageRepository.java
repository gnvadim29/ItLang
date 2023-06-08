package com.itlang.repositories;

import com.itlang.models.Image;
import com.itlang.models.course.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Vadym Hnatiuk
 */
public interface BlogPostImageRepository extends JpaRepository<Image, Long> {
    public void deleteImageById(Long id);
    public Image findImageById(Long id);
    void deleteAllByAnswer(Answer answer);

}
