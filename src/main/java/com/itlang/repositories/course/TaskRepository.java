package com.itlang.repositories.course;

import com.itlang.models.course.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByTypeAndLevel_Id(String type, Long id);
    Task findTaskById(Long id);
    List<Task> findTaskByLevelIdAndType (Long id, String type);
}
