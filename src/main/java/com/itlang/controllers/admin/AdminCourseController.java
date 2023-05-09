package com.itlang.controllers.admin;

import com.itlang.models.course.Course;
import com.itlang.models.course.Level;
import com.itlang.models.course.QuestionBody;
import com.itlang.services.course.CourseService;
import com.itlang.services.course.LevelService;
import com.itlang.services.course.QuestionService;
import com.itlang.services.course.TaskService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AdminCourseController {

    private final CourseService courseService;
    private final LevelService levelService;
    private final TaskService taskService;
    private final QuestionService questionService;


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
    @GetMapping("/admin/courses/{id}/edit")
    private String editCourse(@PathVariable(name = "id") Long id, Model model){
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        model.addAttribute("levels", course.getLevels());
        model.addAttribute("level", new Level());
        return "admin/admin_course_edit";
    }
    // add level
    @PostMapping("/admin/courses/{id}/edit/add_level")
    private String addLevel(@PathVariable(name = "id") Long id,
                            @ModelAttribute (name = "level") Level level
                            ){
        Level level1 = new Level();
        level1.setTitle(level.getTitle());
        courseService.addLevel(id, level1);
        return "redirect:/admin/courses/{id}/edit";
    }
    // delete level
    @GetMapping("/admin/courses/{id}/edit/delete_level/{sid}")
    private String deleteLevel(@PathVariable (name = "id") Long id,
                               @PathVariable (name = "sid") Long sid){
        courseService.dropLevel(sid);
        return "redirect:/admin/courses/{id}/edit";
    }

    //edit level
    @GetMapping("/admin/level/{id}/edit")
    private String editLevel(@PathVariable (name = "id") Long id, Model model){
        model.addAttribute("level", levelService.getLevel(id));
        model.addAttribute("tasks", taskService.getListeningTasks());
        return "admin/admin_level_listening";
    }
    //add task
    @PostMapping("/admin/level/{id}/add_task")
    private String addTask(@PathVariable(name = "id") Long id,
                           @RequestParam (name = "task") String value,
                           @RequestParam (name = "title") String title){
        taskService.createTask(id, value, title);
        return "redirect:/admin/level/{id}/edit";
    }
    // edit task
    @GetMapping("/admin/task/{id}/edit")
    private String editTask(@PathVariable (name = "id") Long id, Model model){
        model.addAttribute("task", taskService.getTaskById(id));
        model.addAttribute("question", new QuestionBody());
        model.addAttribute("questions", questionService.getQuestions(id));
        return "admin/admin_edit_task";
    }
    @PostMapping("/admin/task/{id}/edit/add_question")
    private String addQuestion(@PathVariable (name = "id") Long id,
                               @ModelAttribute (name = "question") QuestionBody questionBody){

        System.out.println("--------------------------------------------------");
        System.out.println(questionBody.getTitle());
        System.out.println(questionBody.getAnswer1());
        System.out.println(questionBody.getAnswer2());
        System.out.println(questionBody.getAnswer3());
        System.out.println(questionBody.getCorrectAnswer());
        System.out.println("--------------------------------------------------");

        questionService.addQuestion(id, questionBody);

        return "redirect:/admin/task/{id}/edit";

    }
}
