package com.dj.v_02.faculty;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    @Transactional
    public ResponseEntity<FacultyResponseDto> create(@RequestBody @Valid FacultyRegisterDto facultyRegisterDto, UriComponentsBuilder uriComponentsBuilder) {
        Faculty faculty = facultyService.create(facultyRegisterDto);
        FacultyResponseDto facultyResponseDto = new FacultyResponseDto(faculty);
        URI url = uriComponentsBuilder.path("/faculty/{id}").buildAndExpand(faculty.getId()).toUri();
        return ResponseEntity.created(url).body(facultyResponseDto);
    }

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FacultyResponseDto>> list() {
        List<Faculty> faculties = facultyService.getFaculties();
        List<FacultyResponseDto> listFaculties = faculties.stream().map(FacultyResponseDto::new).toList();
        return ResponseEntity.ok(listFaculties);
    }
}
