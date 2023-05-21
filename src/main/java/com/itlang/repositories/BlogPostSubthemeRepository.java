package com.itlang.repositories;

import com.itlang.models.BlogPostSubtheme;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Vadym Hnatiuk
 */
public interface BlogPostSubthemeRepository extends JpaRepository<BlogPostSubtheme, Long> {
    public void deleteBlogPostSubthemeById(Long id);
    public BlogPostSubtheme findBlogPostSubthemeById(Long id);
}
