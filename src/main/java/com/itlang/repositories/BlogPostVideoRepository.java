package com.itlang.repositories;

import com.itlang.models.BlogPostVideo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Vadym Hnatiuk
 */
public interface BlogPostVideoRepository extends JpaRepository<BlogPostVideo, Long> {
    public BlogPostVideo findBlogPostVideoById(Long id);
}
