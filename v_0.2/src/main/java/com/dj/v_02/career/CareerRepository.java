package com.dj.v_02.career;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRepository extends JpaRepository<Career, String> {
    List<Career> findAllByFacultyId(String facultyId);
}
