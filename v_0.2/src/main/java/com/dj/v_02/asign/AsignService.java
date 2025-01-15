package com.dj.v_02.asign;

import com.dj.v_02.course.Course;
import com.dj.v_02.course.CourseRepository;
import com.dj.v_02.user.User;
import com.dj.v_02.user.UserRepository;
import com.dj.v_02.user.UserRoleEnum;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsignService {
    private final AsignRepository asignRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public AsignService(AsignRepository asignRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.asignRepository = asignRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public Asign createAsign(AsignRegisterDto asignRegisterDto) {
        Course course = courseRepository.findByCode(asignRegisterDto.courseId());

        if (course == null) {
            throw new EntityNotFoundException("Course not found");
        }

        User user = userRepository.findByUsername(asignRegisterDto.userId());

        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        if (!(user.getRole() == UserRoleEnum.DOCENTE)) {
            throw new EntityNotFoundException("User is not a teacher");
        }

        Optional<Asign> asign = asignRepository.findByCourseAndSectionAndStatus(course, AsignSectionEnum.valueOf(asignRegisterDto.section()), AsignStatusEnum.ACTIVE);

        if (asign.isPresent()) {
            throw new EntityNotFoundException("El curso ya está asignado a un docente");
        }

        return asignRepository.save(new Asign(asignRegisterDto, course, user));
    }

    public List<Asign> findAll() {
        List<Asign> asigs = asignRepository.findAll();
        if (asigs.isEmpty()) {
            throw new EntityNotFoundException("Asigns not found");
        }
        return asigs;
    }

    public List<Asign> findByUserId(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        List<Asign> asigns = asignRepository.findByUserId(userId);
        if (asigns.isEmpty()) {
            throw new EntityNotFoundException("Asigns not found");
        }
        return asigns;
    }
}
