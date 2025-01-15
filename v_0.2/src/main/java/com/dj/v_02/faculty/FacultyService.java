package com.dj.v_02.faculty;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty create(FacultyRegisterDto facultyRegisterDto) {
        return facultyRepository.save(new Faculty(facultyRegisterDto));
    }

    public List<Faculty> getFaculties() {
        List<Faculty> faculties = facultyRepository.findAll();
        if (facultyRepository.findAll().isEmpty()) {
            throw new EntityNotFoundException("Faculties not found");
        }
        return faculties;
    }
}
