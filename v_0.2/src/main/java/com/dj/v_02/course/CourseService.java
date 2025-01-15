package com.dj.v_02.course;

import com.dj.v_02.career.Career;
import com.dj.v_02.career.CareerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CareerRepository careerRepository;

    public CourseService(CourseRepository courseRepository, CareerRepository careerRepository) {
        this.courseRepository = courseRepository;
        this.careerRepository = careerRepository;
    }

    public Course createCourse(CourseRegisterDto courseRegisterDto) {
        Career career = careerRepository.findById(courseRegisterDto.careerId())
                .orElseThrow(() -> new EntityNotFoundException("Career not found"));
        return courseRepository.save(new Course(courseRegisterDto, career));
    }

    public Course findByCode(String code) {
        if (code.isEmpty()) {
            throw new EntityNotFoundException("Code is required");
        }
        Course course = courseRepository.findByCode(code.toUpperCase());

        if (course == null) {
            throw new EntityNotFoundException("Course not found");
        }
        return course;
    }

    public List<Course> findAll() {
        List<Course> courses = courseRepository.findAll();
        if (courses.isEmpty()) {
            throw new EntityNotFoundException("Courses not found");
        }
        return courses;
    }
}
