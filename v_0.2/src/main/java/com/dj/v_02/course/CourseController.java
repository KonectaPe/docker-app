package com.dj.v_02.course;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    @Transactional
    public ResponseEntity<CourseResponseDto> createCourse(@RequestBody @Valid CourseRegisterDto courseRegisterDto, UriComponentsBuilder uriComponentsBuilder) {
        Course course = courseService.createCourse(courseRegisterDto);
        CourseResponseDto courseResponseDto = new CourseResponseDto(course);
        URI url = uriComponentsBuilder.path("/course/{id}").buildAndExpand(course.getId()).toUri();
        return ResponseEntity.created(url).body(courseResponseDto);
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<CourseResponseDto> findByCode(@PathVariable String code) {
        Course course = courseService.findByCode(code);
        CourseResponseDto response = new CourseResponseDto(course);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<List<CourseResponseDto>> findAll() {
        List<Course> courses = courseService.findAll();
        List<CourseResponseDto> response = courses.stream().map(CourseResponseDto::new).toList();
        return ResponseEntity.ok(response);
    }
}
