package com.dj.v_02.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserRegisterDto userRegisterDto) {
        if (userRegisterDto.role() == UserRoleEnum.ADMINISTRATIVO) {
            throw new EntityNotFoundException("Administrative users cannot be created.");
        }
        if (userRegisterDto.role() == UserRoleEnum.SUDO) {
            throw new EntityNotFoundException("Sudo users cannot be created.");
        }
        User user = new User(userRegisterDto);
        user.setPassword(passwordEncoder.encode(userRegisterDto.password()));
        return userRepository.save(user);
    }

    public User createSudo(UserRegisterDto userRegisterDto) {
        if (userRegisterDto.role() != UserRoleEnum.ADMINISTRATIVO) {
            throw new EntityNotFoundException("Only administrative users can be created.");
        }
        User user = new User(userRegisterDto);
        user.setPassword(passwordEncoder.encode(userRegisterDto.password()));
        return userRepository.save(user);
    }

    public List<User> getStudents(UserRoleEnum studentRole) {
        return userRepository.findByRole(studentRole);
    }

    public List<User> getTeachers(UserRoleEnum teacherRole) {
        return userRepository.findByRole(teacherRole);
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found."));
    }

    public void updatePassword(String id, String password) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found."));
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User not found.");
        }
        return user;
    }
}
