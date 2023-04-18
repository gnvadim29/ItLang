package com.itlang.repositories;

import com.itlang.models.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Vadym Hnatiuk
 */
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    BlogPost findBlogPostById(Long id);
    List<BlogPost> findAllByOrderByIdDesc();
}
