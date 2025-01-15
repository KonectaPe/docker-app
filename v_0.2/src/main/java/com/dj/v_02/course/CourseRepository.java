package com.dj.v_02.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findAllByCareerId(String careerId);

    Course findByCode(String code);
}
