package com.dj.v_02.enrollment;

import com.dj.v_02.course.Course;
import com.dj.v_02.user.User;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {

    @Query("SELECT e FROM Enrollment e WHERE e.user.id = :userId ORDER BY e.course.code, e.year, e.cycle")
    List<Enrollment> findAllByUserId(String userId);

    List<Enrollment> findAllByUser_Username(@Size(min = 8, max = 20) String userUsername);

    List<Enrollment> findAllByCourseId(String courseId);

    List<Enrollment> findByUserAndCourse(User user, Course course);

    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.course.id = :courseId AND e.status = 'FAILED'")
    int countFailedEnrollmentByCourseId(@Param("courseId") String courseId);

    @Query("SELECT e FROM Enrollment e WHERE e.course.id = :courseId AND e.section = :section ORDER BY e.course.code, e.year, e.cycle")
    List<Enrollment> findAllByCourseIdAndSection(@Param("courseId") String courseId, @Param("section") EnrollmentSectionEnum section);
}
