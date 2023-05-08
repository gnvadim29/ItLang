package com.itlang.controllers.admin;

import com.itlang.models.course.Course;
import com.itlang.services.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AdminCourseController {

    private final CourseService courseService;

    @GetMapping("/admin/courses")
    private String getCourses(Model model){
        model.addAttribute("courses", courseService.getCourses());
        model.addAttribute("course", new Course());
        return "admin/admin_courses";
    }
    @PostMapping("/admin/courses/add_course")
    private String addCourse(@ModelAttribute(name = "course") Course course){
        courseService.createCourse(course);
        return "redirect:/admin/courses";
    }
    @GetMapping("/admin/courses/{id}/delete")
    private String deleteCourse(@PathVariable(name = "id") Long id){
        courseService.dropCourse(id);
        return "redirect:/admin/courses";
    }
}
