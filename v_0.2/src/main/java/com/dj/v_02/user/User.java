package com.dj.v_02.user;

import com.dj.v_02.asign.Asign;
import com.dj.v_02.enrollment.Enrollment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "full_name", nullable = false, unique = true, length = 100)
    private String fullName;
    @Column(nullable = false, unique = true, length = 20)
    @Size(min = 8, max = 20)
    private String username;
    @Column(nullable = false, length = 50)
    @Size(min = 8, max = 255)
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asign> asigns;

    public User(UserRegisterDto userRegisterDto) {
        this.fullName = userRegisterDto.fullName();
        this.username = userRegisterDto.username();
        this.password = userRegisterDto.password();
        this.role = userRegisterDto.role();
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.getRole()));
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
