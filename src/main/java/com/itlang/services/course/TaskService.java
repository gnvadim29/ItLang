package com.itlang.services.course;

import com.itlang.models.course.Level;
import com.itlang.models.course.Task;
import com.itlang.repositories.course.LevelRepository;
import com.itlang.repositories.course.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final LevelRepository levelRepository;


    public void createTask(Long id, String value, String title) {
        Task task = new Task();
        task.setTitle(title);
        if(value.equals("listening_text_more_answer") || value.equals("listening_true_false")
                || value.equals("listening_text_answer") || value.equals("listening_image_answer")){
            task.setType("listening");
            task.setDescription(value);
        }
        Level level = levelRepository.findLevelById(id);
        level.addTask(task);
        levelRepository.save(level);
    }
    public List<Task> getListeningTasks(){
        return taskRepository.findAllByType("listening");
    }
    public Task getTaskById(Long id){
        return  taskRepository.findTaskById(id);
    }
}
