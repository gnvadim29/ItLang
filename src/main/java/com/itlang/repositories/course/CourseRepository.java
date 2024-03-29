package com.itlang.repositories.course;

import com.itlang.models.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findCourseById(Long id);
    Course findCourseByCourseUrl(String url);
}
