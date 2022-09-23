package com.example.scheduleProject.api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {

    @NonNull
    Long id;

    @NonNull
    String courseIdentity;
}
