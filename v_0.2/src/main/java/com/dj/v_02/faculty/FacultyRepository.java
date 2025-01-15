package com.dj.v_02.faculty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, String> {
}
