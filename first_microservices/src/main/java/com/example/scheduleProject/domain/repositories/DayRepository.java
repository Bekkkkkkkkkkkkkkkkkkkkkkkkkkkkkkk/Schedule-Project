package com.example.scheduleProject.domain.repositories;

import com.example.scheduleProject.domain.entities.DayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<DayEntity, Long> {
}
