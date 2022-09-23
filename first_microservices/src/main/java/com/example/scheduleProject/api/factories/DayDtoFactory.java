package com.example.scheduleProject.api.factories;

import com.example.scheduleProject.api.dto.DayDto;
import com.example.scheduleProject.domain.entities.DayEntity;
import org.springframework.stereotype.Component;

@Component
public class DayDtoFactory {

    public DayDto makeDayDto(DayEntity entity) {

        return DayDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
