package com.dj.v_02.asign;

import com.dj.v_02.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AsignRepository extends JpaRepository<Asign, String> {

    Optional<Asign> findByCourseAndSectionAndStatus(Course course, AsignSectionEnum section, AsignStatusEnum status);

    List<Asign> findByUserId(String userId);
}
