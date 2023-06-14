package com.itlang;

import com.itlang.models.course.Course;
import com.itlang.models.course.Task;
import com.itlang.repositories.course.CourseRepository;
import com.itlang.repositories.course.TaskRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class ItLangApplication {
	private final CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(ItLangApplication.class, args);
	}
	@PostConstruct
	public void onApplicationStart() {
		List<Course> courses = courseRepository.findAll();
		if (courses.isEmpty()){
			Course zno = new Course();
			Course it = new Course();

			zno.setTitle("ZNO English");
			it.setTitle("IT English");

			zno.setCourseUrl("znoenglish");
			it.setCourseUrl("itenglish");

			courseRepository.save(zno);
			courseRepository.save(it);
		}
	}
}
