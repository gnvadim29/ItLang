package com.itlang.repositories.course;

import com.itlang.models.course.Course;
import com.itlang.models.course.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
    public Level findLevelById(Long id);

}
