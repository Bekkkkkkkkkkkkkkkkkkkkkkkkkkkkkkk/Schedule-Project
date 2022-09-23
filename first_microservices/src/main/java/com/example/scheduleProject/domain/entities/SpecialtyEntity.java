package com.example.scheduleProject.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "specialty")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpecialtyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String name;

    String description;

    @JsonProperty("created_at")
    Instant createdAt = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    CourseEntity course;
}
