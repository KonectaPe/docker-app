package com.dj.v_02.asign;

import com.dj.v_02.course.Course;
import com.dj.v_02.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "asign")
@Entity(name = "Asign")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Asign {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Enumerated(EnumType.STRING)
    private AsignSectionEnum section;
    @Enumerated(EnumType.STRING)
    private AsignStatusEnum status;
    private LocalDateTime createdAt;

    public Asign(AsignRegisterDto asignRegisterDto, Course course, User user) {
        this.course = course;
        this.user = user;
        this.section = AsignSectionEnum.valueOf(asignRegisterDto.section());
        this.status = AsignStatusEnum.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }
}
