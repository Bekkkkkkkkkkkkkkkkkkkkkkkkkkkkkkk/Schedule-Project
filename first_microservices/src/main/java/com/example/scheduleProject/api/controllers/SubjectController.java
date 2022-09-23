package com.example.scheduleProject.api.controllers;

import com.example.scheduleProject.api.dto.AskDto;
import com.example.scheduleProject.api.dto.SubjectDto;
import com.example.scheduleProject.api.exceptions.BadRequestException;
import com.example.scheduleProject.api.exceptions.NotFoundException;
import com.example.scheduleProject.api.factories.SubjectDtoFactory;
import com.example.scheduleProject.domain.entities.SubjectEntity;
import com.example.scheduleProject.domain.repositories.SubjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class SubjectController {

    SubjectRepository subjectRepository;

    SubjectDtoFactory subjectDtoFactory;

    public static final String CREATE_SUBJECT = "/api/subjects/post";
    public static final String FETCH_SUBJECT = "/api/subjects/";
    public static final String EDIT_SUBJECT = "/api/subjects/edit/{subject_id}";
    public static final String DELETE_SUBJECT = "/api/subjects/delete/{subject_id}";

    /*@GetMapping(FETCH_SUBJECT)
    public List<SubjectDto> fetchSubject(
            @RequestParam(value = "prefix_name", required = false)Optional<String> optionalPrefixName){

        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

        Stream<SubjectEntity> subjectStream = optionalPrefixName
                .map(subjectRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(subjectRepository::streamAll);

        return subjectStream
                .map(subjectDtoFactory::makeSubjectDto)
                .collect(Collectors.toList());
    }*/

    @PostMapping(CREATE_SUBJECT)
    public SubjectDto createSubject(
            @RequestParam String name,
            @RequestParam String lesson,
            @RequestParam String location,
            @RequestParam String teacher){

        if(name.trim().isEmpty()){
            throw new BadRequestException("Name can't be empty.");
        }

        subjectRepository
                .findByName(name)
                .ifPresent(subject -> {
                    throw new BadRequestException(
                            String.format(
                                    "Subject \"%s\" already exist.",
                                    name
                            )
                    );
                });

        SubjectEntity subject = subjectRepository.saveAndFlush(
                SubjectEntity.builder()
                .name(name)
                .lesson(lesson)
                .location(location)
                .teacher(teacher)
                .build()
        );
        return subjectDtoFactory.makeSubjectDto(subject);
    }

    @PatchMapping(EDIT_SUBJECT)
    public SubjectDto editSubject(
            @PathVariable("subject_id") Long subjectId,
            @RequestParam String name,
            @RequestParam String lesson,
            @RequestParam String location,
            @RequestParam String teacher) {

        if(name.trim().isEmpty()){
            throw new BadRequestException("Name can't be empty.");
        }

        SubjectEntity subject = getSubjectOrThrowException(subjectId);

        subjectRepository
                .findByName(name)
                .filter(anotherSubject -> !Objects.equals(anotherSubject.getId(), subjectId))
                .ifPresent(anotherSubject -> {
                    throw new BadRequestException(String.format("Subject \"%s\" already exist.", name));
                });

        subject.setName(name);
        subject.setName(lesson);
        subject.setName(location);
        subject.setName(teacher);

        subject = subjectRepository.saveAndFlush(subject);

        return subjectDtoFactory.makeSubjectDto(subject);
    }

    @DeleteMapping(DELETE_SUBJECT)
    public AskDto deleteSubject(
            @PathVariable("subject_id") Long subjectId){

        getSubjectOrThrowException(subjectId);

        subjectRepository.deleteById(subjectId);

        return AskDto.makeDefault(true);
    }

    private SubjectEntity getSubjectOrThrowException(Long subjectId) {
        return subjectRepository
                .findById(subjectId)
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format(
                                        "Project with \"$s\" doesn't exist.",
                                        subjectId
                                )
                        )
                );
    }
}
