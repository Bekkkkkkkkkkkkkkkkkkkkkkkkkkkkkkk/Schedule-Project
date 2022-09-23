package com.example.scheduleProject.domain.repositories;

import com.example.scheduleProject.domain.entities.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
}
