package com.itlang.controllers;

import com.itlang.models.Person;
import com.itlang.models.course.Course;
import com.itlang.repositories.PeopleRepository;
import com.itlang.repositories.course.LevelRepository;
import com.itlang.services.course.CourseService;
import com.itlang.services.course.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final TaskService taskService;
    private final CourseService courseService;
    private final PeopleRepository peopleRepository;
    private final LevelRepository levelRepository;

    @GetMapping("/{url}")
    public String getCourse(@PathVariable( name = "url") String url, Model model){
        Course course = courseService.getCourseByUrl(url);
        model.addAttribute("course", course);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = peopleRepository.findPersonByEmail(authentication.getName());
        model.addAttribute("user", person);
        if (course!=null){
            return "course/course";
        }else return "course/not_found";
    }

    @GetMapping("/{course_url}/level/{id}/listening")
    public String getListening(@PathVariable(name = "id") Long id, Model model, @PathVariable String course_url){
        Course course = courseService.getCourseByUrl(course_url);
        model.addAttribute("course", course);

        model.addAttribute("tasks", taskService.getListeningTasksByLevelId(id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = peopleRepository.findPersonByEmail(authentication.getName());
        model.addAttribute("user", person);

        String levelTitle = levelRepository.findLevelById(id).getTitle();
        model.addAttribute("level", levelTitle);
        return "course/listening";
    }

    @GetMapping("/{course_url}/level/{id}/reading")
    public String getReading(@PathVariable(name = "id") Long id, Model model, @PathVariable String course_url){
        Course course = courseService.getCourseByUrl(course_url);
        model.addAttribute("course", course);

        model.addAttribute("tasks", taskService.getReadingTasksByLevelId(id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = peopleRepository.findPersonByEmail(authentication.getName());
        model.addAttribute("user", person);

        String levelTitle = levelRepository.findLevelById(id).getTitle();
        model.addAttribute("level", levelTitle);
        return "course/reading";
    }

    @GetMapping("/{course_url}/level/{id}/use_of_English")
    public String getUse(@PathVariable(name = "id") Long id, Model model, @PathVariable String course_url){
        Course course = courseService.getCourseByUrl(course_url);
        model.addAttribute("course", course);

        model.addAttribute("tasks", taskService.getUseTasksByLevelId(id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = peopleRepository.findPersonByEmail(authentication.getName());
        model.addAttribute("user", person);

        String levelTitle = levelRepository.findLevelById(id).getTitle();
        model.addAttribute("level", levelTitle);
        return "course/use_of_english";
    }


    @GetMapping("/{course_url}/level/{id}/writing")
    public String getWriting(@PathVariable(name = "id") Long id, Model model, @PathVariable String course_url){
        Course course = courseService.getCourseByUrl(course_url);
        model.addAttribute("course", course);

        model.addAttribute("tasks", taskService.getWritingTasksByLevelId(id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = peopleRepository.findPersonByEmail(authentication.getName());
        model.addAttribute("user", person);

        String levelTitle = levelRepository.findLevelById(id).getTitle();
        model.addAttribute("level", levelTitle);
        return "course/writing";
    }





//    @PostMapping("/check")
//    public String check(@RequestParam String answer0,
//                        @RequestParam String answer1,
//                        @RequestParam String answer2,
//                        @RequestParam String answer3,
//                        @RequestParam String answer4,
//                        @RequestParam String answer5) {
//        System.out.println(answer0);
//        System.out.println(answer1);
//        System.out.println(answer2);
//        System.out.println(answer3);
//        System.out.println(answer4);
//        System.out.println(answer5);
//
//        return "index";
//
//    }



}
