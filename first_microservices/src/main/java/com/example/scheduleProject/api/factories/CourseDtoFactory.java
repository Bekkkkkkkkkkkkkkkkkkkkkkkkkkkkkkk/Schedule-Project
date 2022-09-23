package com.example.scheduleProject.api.factories;

import com.example.scheduleProject.api.dto.CourseDto;
import com.example.scheduleProject.domain.entities.CourseEntity;
import org.springframework.stereotype.Component;

@Component
public class CourseDtoFactory {

    public CourseDto makeCourseDto(CourseEntity entity) {

        return CourseDto.builder()
                .id(entity.getId())
                .courseIdentity(entity.getCourseIdentity())
                .build();
    }
}
