package com.itlang.controllers.admin;

import com.itlang.models.course.*;
import com.itlang.services.course.CourseService;
import com.itlang.services.course.LevelService;
import com.itlang.services.course.QuestionService;
import com.itlang.services.course.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        model.addAttribute("listening_tasks", taskService.getListeningTasks(id));
        model.addAttribute("reading_tasks", taskService.getReadingTasks(id));
        model.addAttribute("useOfEnglish_tasks", taskService.getUseOfEnglish(id));
        model.addAttribute("writing_tasks", taskService.getWritingTasks(id));
        return "admin/admin_level";
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
                               @ModelAttribute (name = "question") QuestionBody questionBody,
                               @RequestParam (name = "type") String type,
                               @RequestParam(name = "image1", required = false) MultipartFile image1,
                               @RequestParam(name = "image2", required = false) MultipartFile image2,
                               @RequestParam(name = "image3", required = false) MultipartFile image3) throws IOException {

        questionService.addQuestion(id, questionBody, type, image1, image2, image3);
        return "redirect:/admin/task/{id}/edit";
    }
    @GetMapping("/admin/level/{id}/edit/delete_task/{sid}")
    public String deleteTask(@PathVariable (name = "id") Long id, @PathVariable(name = "sid") Long sid){
        taskService.deleteTask(sid);
        return "redirect:/admin/level/{id}/edit";

    }
    @PostMapping("/admin/task/{id}/save")
    public String saveTask(@PathVariable (name = "id") Long id,
                           @ModelAttribute (name = "task") Task task){
        String sid = String.valueOf(taskService.saveTask(id, task));
        return "redirect:/admin/task/" + sid + "/edit";
    }
    @GetMapping("/admin/task/{id}/edit/question/{sid}/delete")
    public String deleteQuestion(@PathVariable (name = "sid") Long sid){
        questionService.deleteQuestion(sid);
        return "redirect:/admin/task/{id}/edit";
    }

}
