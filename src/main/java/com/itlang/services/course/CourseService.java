package com.itlang.services.course;

import com.itlang.models.course.Course;
import com.itlang.repositories.course.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public void createCourse(Course course) {
        courseRepository.save(course);
    }

    public void dropCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
