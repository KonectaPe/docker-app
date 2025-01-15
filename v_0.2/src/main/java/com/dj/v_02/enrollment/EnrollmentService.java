package com.dj.v_02.enrollment;

import com.dj.v_02.course.Course;
import com.dj.v_02.course.CourseRepository;
import com.dj.v_02.course.SemesterEnum;
import com.dj.v_02.user.User;
import com.dj.v_02.user.UserRepository;
import com.dj.v_02.user.UserRoleEnum;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public Enrollment createEnrollment(EnrollmentRegisterDto enrollmentRegisterDto) {
        Course course = courseRepository.findByCode(enrollmentRegisterDto.courseId());

        if (course == null) {
            throw new EntityNotFoundException("Course not found");
        }

        User user = userRepository.findByUsername(enrollmentRegisterDto.userId());

        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        boolean isValidSemester = false;
        for (SemesterEnum semester : SemesterEnum.values()) {
            if (course.getSemester() == semester) {
                isValidSemester = true;
                break;
            }
        }

        if (!isValidSemester) {
            throw new EntityNotFoundException("Course is not available this semester");
        }

        if (user.getRole() != UserRoleEnum.ESTUDIANTE) {
            throw new EntityNotFoundException("User is not a student");
        }

        List<Enrollment> existingEnrollment = enrollmentRepository.findByUserAndCourse(user, course);

        if (!existingEnrollment.isEmpty()) {
            if (existingEnrollment.stream().map(Enrollment::getStatus).anyMatch(status -> status == EnrollmentStatusEnum.PASSED)) {
                throw new EntityNotFoundException("User has already passed this course");
            }
            if (existingEnrollment.stream().map(Enrollment::getStatus).anyMatch(status -> status == EnrollmentStatusEnum.PROGRESS)) {
                throw new EntityNotFoundException("User is already enrolled in this course");
            }
            if (enrollmentRepository.countFailedEnrollmentByCourseId(course.getId()) >= 3) {
                throw new EntityNotFoundException("User has failed this course 3 times");
            }
            if (enrollmentRepository.countFailedEnrollmentByCourseId(course.getId()) >= 1) {
                return enrollmentRepository.save(new Enrollment(enrollmentRegisterDto, course, user, enrollmentRepository.countFailedEnrollmentByCourseId(course.getId()) + 1, EnrollmentStatusEnum.PROGRESS));
            }
        } else {
            return enrollmentRepository.save(new Enrollment(enrollmentRegisterDto, course, user, 1, EnrollmentStatusEnum.PROGRESS));
        }
        throw new EntityNotFoundException("User is already enrolled in this course");
    }

    public List<Enrollment> findAllByUsername(String username) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
        if (user.isEmpty()) {
            throw new EntityNotFoundException("Enrollment not found");
        }
        if (user.get().getRole() != UserRoleEnum.ESTUDIANTE) {
            throw new EntityNotFoundException("User is not a student");
        }
        return enrollmentRepository.findAllByUserId(user.get().getId());
    }

    public List<Enrollment> findAllByCourseId(String courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isEmpty()) {
            throw new EntityNotFoundException("Enrollment not found");
        }
        return enrollmentRepository.findAllByCourseId(courseId);
    }

    public List<Enrollment> findAll() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        if (enrollments.isEmpty()) {
            throw new EntityNotFoundException("Enrollments not found");
        }
        return enrollments;
    }

    public List<Enrollment> findAllCoursesByCourseIdAndSection(String courseId, String section) {
        return enrollmentRepository.findAllByCourseIdAndSection(courseId, EnrollmentSectionEnum.valueOf(section));
    }

    public Enrollment updateNotes(UpdateRegisterNotesDto updateRegisterNotesDto) {
        Optional<Enrollment> enrollment = enrollmentRepository.findById(updateRegisterNotesDto.id());
        if (enrollment.isEmpty()) {
            throw new EntityNotFoundException("Enrollment not found");
        }

        if (!Objects.equals(enrollment.get().getUser().getFullName(), updateRegisterNotesDto.codeUser())) {
            throw new EntityNotFoundException("User not found");
        }

        if (updateRegisterNotesDto.unit().isEmpty()) {
            throw new EntityNotFoundException("Unit is too short");
        }

        if (updateRegisterNotesDto.notes().isEmpty()) {
            throw new EntityNotFoundException("Notes are too short");
        }


        switch (updateRegisterNotesDto.unit()) {
            case "1" -> {
                enrollment.get().setNote1(updateRegisterNotesDto.notes());
            }
            case "2" -> {
                enrollment.get().setNote2(updateRegisterNotesDto.notes());
            }
            case "3" -> {
                enrollment.get().setNote3(updateRegisterNotesDto.notes());
            }
            default -> throw new EntityNotFoundException("Invalid unit");
        }

        int totalNotes = 0;
        int sum = 0;

        if (enrollment.get().getNote1() != null) {
            System.out.println("Nota 1");
            totalNotes++;
            sum += Integer.parseInt(enrollment.get().getNote1());
        }

        if (enrollment.get().getNote2() != null) {
            System.out.println("Nota 2");
            totalNotes++;
            sum += Integer.parseInt(enrollment.get().getNote2());
        }

        if (enrollment.get().getNote3() != null) {
            System.out.println("Nota 3");
            totalNotes++;
            sum += Integer.parseInt(enrollment.get().getNote3());
        }

        if (totalNotes == Integer.parseInt(enrollment.get().getUnit())) {
            if (sum / totalNotes >= 11) {
                enrollment.get().setAverage(String.valueOf(sum / totalNotes));
                enrollment.get().setStatus(EnrollmentStatusEnum.PASSED);
            } else {
                enrollment.get().setAverage(String.valueOf(sum / totalNotes));
                enrollment.get().setStatus(EnrollmentStatusEnum.FAILED);
            }
        }
        return enrollmentRepository.save(enrollment.get());
    }
}
