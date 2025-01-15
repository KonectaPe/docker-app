package com.dj.v_02;

import com.dj.v_02.user.User;
import com.dj.v_02.user.UserRegisterDto;
import com.dj.v_02.user.UserRepository;
import com.dj.v_02.user.UserRoleEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ApplicationStartupRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String fullName = "root";
        String username = "superoot";
        String password = "supersudo";

        if (userRepository.findByUsername(username) == null) {
            UserRegisterDto sudoUser = new UserRegisterDto(fullName, username, passwordEncoder.encode(password), UserRoleEnum.SUDO);
            User user = new User(sudoUser);
            userRepository.save(user);
        }
    }
}
