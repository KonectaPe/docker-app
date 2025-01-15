package com.dj.v_02.enrollment;

import com.dj.v_02.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    @Transactional
    public ResponseEntity<EnrollmentResponseDto> createEnrollment(@RequestBody @Valid EnrollmentRegisterDto enrollmentRegisterDto, UriComponentsBuilder uriComponentsBuilder) {
        Enrollment enrollment = enrollmentService.createEnrollment(enrollmentRegisterDto);
        EnrollmentResponseDto enrollmentResponseDto = new EnrollmentResponseDto(enrollment);
        URI url = uriComponentsBuilder.path("/enrollments/{id}").buildAndExpand(enrollment.getId()).toUri();
        return ResponseEntity.created(url).body(enrollmentResponseDto);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<List<EnrollmentResponseDto>> findAll() {
        List<Enrollment> enrollments = enrollmentService.findAll();
        List<EnrollmentResponseDto> response = enrollments.stream().map(EnrollmentResponseDto::new).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<List<EnrollmentResponseDto>> findAllByUserId(@PathVariable String userId) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!authenticatedUser.getId().equals(userId)) {
            throw new EntityNotFoundException("User not found");
        }

        List<Enrollment> enrollments = enrollmentService.findAllByCourseId(userId);
        List<EnrollmentResponseDto> response = enrollments.stream().map(EnrollmentResponseDto::new).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<List<EnrollmentResponseDto>> findAllByUsername(@PathVariable String username) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!authenticatedUser.getUsername().equals(username)) {
            throw new EntityNotFoundException("User not found");
        }

        List<Enrollment> enrollments = enrollmentService.findAllByUsername(username);
        List<EnrollmentResponseDto> response = enrollments.stream().map(EnrollmentResponseDto::new).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<EnrollmentResponseDto>> findAllByCourseId(@PathVariable String courseId) {
        List<Enrollment> enrollments = enrollmentService.findAllByCourseId(courseId);
        List<EnrollmentResponseDto> response = enrollments.stream().map(EnrollmentResponseDto::new).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/course/{courseId}/section/{section}")
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<List<EnrollmentResponseDto>> findAllByCourseIdAndSection(@PathVariable String courseId, @PathVariable String section) {
        List<Enrollment> enrollments = enrollmentService.findAllCoursesByCourseIdAndSection(courseId, section);
        List<EnrollmentResponseDto> response = enrollments.stream().map(EnrollmentResponseDto::new).toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<EnrollmentResponseDto> updateNotes(@RequestBody @Valid UpdateRegisterNotesDto updateRegisterNotesDto) {
        Enrollment enrollment = enrollmentService.updateNotes(updateRegisterNotesDto);
        return ResponseEntity.ok(new EnrollmentResponseDto(enrollment));
    }
}
