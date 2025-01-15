package com.dj.v_02.career;

import com.dj.v_02.faculty.Faculty;
import com.dj.v_02.faculty.FacultyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CareerService {
    private final CareerRepository careerRepository;
    private final FacultyRepository facultyRepository;

    public CareerService(CareerRepository careerRepository, FacultyRepository facultyRepository) {
        this.careerRepository = careerRepository;
        this.facultyRepository = facultyRepository;
    }

    public Career create(CareerRegisterDto careerRegisterDto) {
        Faculty faculty = facultyRepository.findById(careerRegisterDto.facultyId())
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found"));
        return careerRepository.save(new Career(careerRegisterDto, faculty));
    }

    public List<Career> findAllByFacultyId(String facultyId) {
        Optional<Faculty> faculty = facultyRepository.findById(facultyId);
        if (faculty.isEmpty()) {
            throw new EntityNotFoundException("Faculty not found");
        }
        List<Career> careers = careerRepository.findAllByFacultyId(facultyId);
        if (careers.isEmpty()) {
            throw new EntityNotFoundException("Careers not found");
        }
        return careers;
    }

    public List<Career> getCareers() {
        List<Career> careers = careerRepository.findAll();
        if (careers.isEmpty()) {
            throw new EntityNotFoundException("Careers not found");
        }
        return careers;
    }
}
