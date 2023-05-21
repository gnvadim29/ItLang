package com.itlang.repositories;
import com.itlang.models.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
}
