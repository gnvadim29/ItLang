package com.itlang.services.course;

import com.itlang.models.course.Level;
import com.itlang.repositories.course.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LevelService {
    private final LevelRepository levelRepository;


    public Level getLevel(Long id) {
        return levelRepository.findLevelById(id);
    }
}
