package com.dj.v_02.career;

import com.dj.v_02.course.Course;
import com.dj.v_02.faculty.Faculty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "careers")
@Entity(name = "Career")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;
    @OneToMany(mappedBy = "career", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Course> courses;

    public Career(CareerRegisterDto careerRegisterDto, Faculty faculty) {
        this.name = careerRegisterDto.name().toUpperCase();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.faculty = faculty;
    }
}
