package com.itlang.controllers;

import com.itlang.models.Person;
import com.itlang.models.course.CheckQuestion;
import com.itlang.models.course.Course;
import com.itlang.repositories.PeopleRepository;
import com.itlang.repositories.course.LevelRepository;
import com.itlang.repositories.course.UserQuestionsRepository;
import com.itlang.services.course.CourseService;
import com.itlang.services.course.QuestionService;
import com.itlang.services.course.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Controller
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final TaskService taskService;
    private final CourseService courseService;
    private final PeopleRepository peopleRepository;
    private final LevelRepository levelRepository;
    private final QuestionService questionService;
    private final UserQuestionsRepository userQuestionsRepository;


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

        model.addAttribute("user_questions", userQuestionsRepository.findUserQuestionsByPersonAndLevelId(person, id));
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

    @PostMapping("/{course_url}/level/{id}/{level_type}/check")
    public String check(HttpServletRequest request, @RequestParam(name = "type") String type) {
        List<CheckQuestion> answers = new ArrayList<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (paramName.startsWith("question")) {
                Long answer = Long.valueOf(request.getParameter(paramName));
                Long questionId = Long.parseLong(paramName.substring("question".length()));
                CheckQuestion question = new CheckQuestion();
                question.setUserAnswer(answer);
                question.setId(questionId);
                answers.add(question);
            }
        }
        questionService.checkQuestions(answers, type);
        System.out.println("------------------------------");
        for (int i = 0; i < answers.size(); i++){
            System.out.println(answers.get(i).getId());
            System.out.println(answers.get(i).getUserAnswer());
        }
        System.out.println("------------------------------");
        return "redirect:/course/{course_url}/level/{id}/{level_type}";
    }


}
