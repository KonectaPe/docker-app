package com.dj.v_02.career;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/careers")
public class CareerController {
    private final CareerService careerService;

    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    @Transactional
    public ResponseEntity<CareerResponseDto> create(@RequestBody @Valid CareerRegisterDto careerRegisterDto, UriComponentsBuilder uriComponentsBuilder) {
        Career career = careerService.create(careerRegisterDto);
        CareerResponseDto careerResponseDto = new CareerResponseDto(career);
        URI url = uriComponentsBuilder.path("/careers/{id}").buildAndExpand(careerResponseDto.id()).toUri();
        return ResponseEntity.created(url).body(careerResponseDto);
    }

    @GetMapping("/faculty/{facultyId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CareerResponseDto>> findAllByFacultyId(@PathVariable String facultyId) {
        List<Career> careers = careerService.findAllByFacultyId(facultyId);
        List<CareerResponseDto> response = careers.stream().map(CareerResponseDto::new).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CareerResponseDto>> list() {
        List<Career> careers = careerService.getCareers();
        List<CareerResponseDto> listCareers = careers.stream().map(CareerResponseDto::new).toList();
        return ResponseEntity.ok(listCareers);
    }
}
