package com.itlang.services.course;

import com.itlang.models.course.Course;
import com.itlang.models.course.Level;
import com.itlang.repositories.course.CourseRepository;
import com.itlang.repositories.course.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final LevelRepository levelRepository;

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public void createCourse(Course course) {
        String courseUrl = course.getTitle();
        courseUrl = courseUrl.toLowerCase().replaceAll("\\s", "");
        course.setCourseUrl(courseUrl);
        courseRepository.save(course);
    }

    public void dropCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public Course getCourseById(Long id) {
        return courseRepository.findCourseById(id);
    }

    public void addLevel(Long id, Level level) {
        Course course = courseRepository.findCourseById(id);

        course.addLevel(level);
        courseRepository.save(course);
    }

    public void dropLevel(Long sid) {
        levelRepository.deleteById(sid);
    }
    public Course getCourseByUrl(String courseUrl){
        return courseRepository.findCourseByCourseUrl(courseUrl);
    }
}
