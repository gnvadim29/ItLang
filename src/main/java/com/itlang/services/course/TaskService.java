package com.itlang.services.course;

import com.itlang.models.course.Level;
import com.itlang.models.course.Task;
import com.itlang.repositories.course.CourseRepository;
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
    private final CourseRepository courseRepository;


    public void createTask(Long id, String value, String title) {
        Task task = new Task();
        task.setTitle(title);
        if(value.equals("listening_text_more_answer") || value.equals("listening_true_false")
                || value.equals("listening_text_answer") || value.equals("listening_image_answer")){
            task.setType("listening");
        } else if(value.equals("reading_text_answers") || value.equals("reading_texts_answer")){
            task.setType("reading");
        } else if(value.equals("use_text_paste") || value.equals("use_text_answers")){
            task.setType("useOfEnglish");
        } else if(value.equals("writing")){
            task.setType("writing");
        }

        task.setDescription(value);
        Level level = levelRepository.findLevelById(id);
        level.addTask(task);
        levelRepository.save(level);
    }
    public List<Task> getListeningTasks(Long id){
        return taskRepository.findAllByTypeAndLevel_Id("listening", id);
    }
    public Task getTaskById(Long id){
        return  taskRepository.findTaskById(id);
    }

    public void deleteTask(Long sid) {
        taskRepository.deleteById(sid);
    }

    public Long saveTask(Long id, Task task) {
        Task task1 = taskRepository.findTaskById(id);
        task1.setTitle(task.getTitle());
        task1.setMediaUrl(task.getMediaUrl());
        task1.setTextDescription(task.getTextDescription());
        task1.setText(task.getText());

        taskRepository.save(task1);
        return task1.getLevel().getId();
    }

    public Object getReadingTasks(Long id) {
        return taskRepository.findAllByTypeAndLevel_Id("reading", id);
    }

    public List<Task> getListeningTasksByLevelId(Long id) {
        return taskRepository.findTaskByLevelIdAndType(id, "listening");
    }



    public List<Task> getReadingTasksByLevelId(Long id) {
        return taskRepository.findTaskByLevelIdAndType(id, "reading");
    }

    public List<Task> getUseOfEnglish(Long id) {
        return taskRepository.findAllByTypeAndLevel_Id("useOfEnglish", id);
    }

    public Object getUseTasksByLevelId(Long id) {
        return taskRepository.findTaskByLevelIdAndType(id, "useOfEnglish");

    }

    public List<Task> getWritingTasks(Long id) {
        return taskRepository.findAllByTypeAndLevel_Id("writing", id);
    }

    public Object getWritingTasksByLevelId(Long id) {
        return taskRepository.findTaskByLevelIdAndType(id, "writing");
    }
}
