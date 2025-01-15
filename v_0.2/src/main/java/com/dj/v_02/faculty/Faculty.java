package com.dj.v_02.faculty;

import com.dj.v_02.career.Career;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "faculties")
@Entity(name = "Faculty")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[a-zA-Z áéíóúÁÉÍÓÚñÑ]*$")
    private String name;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Career> careers;

    public Faculty(FacultyRegisterDto facultyRegisterDto) {
        this.name = facultyRegisterDto.name().toUpperCase();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
