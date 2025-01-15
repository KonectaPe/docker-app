package com.dj.v_02.user;

import com.dj.v_02.infra.jwt.JwtService;
import com.dj.v_02.infra.jwt.TokenRegisterDto;
import com.dj.v_02.infra.jwt.TokenResponseDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    @Transactional
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid UserRegisterDto userRegisterDto, UriComponentsBuilder uriComponentsBuilder) {
        User user = userService.createUser(userRegisterDto);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        URI url = uriComponentsBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(url).body(userResponseDto);
    }

    @PostMapping("/sudo")
    @PreAuthorize("hasRole('SUDO')")
    @Transactional
    public ResponseEntity<UserResponseDto> createSudo(@RequestBody @Valid UserRegisterDto userRegisterDto, UriComponentsBuilder uriComponentsBuilder) {
        User user = userService.createSudo(userRegisterDto);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        URI url = uriComponentsBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(url).body(userResponseDto);
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<Object> login(@RequestBody(required = false) @Valid UserLoginDto userLoginDto) {
        if (userLoginDto == null) {
            return ResponseEntity.badRequest().body("The request body cannot be empty.");
        }
        Authentication authToken = new UsernamePasswordAuthenticationToken(userLoginDto.username(), userLoginDto.password());
        Authentication authentication = authenticationManager.authenticate(authToken);
        User user = (User) authentication.getPrincipal();
        if (userLoginDto.role() != null && !userLoginDto.role().equals(user.getRole())) {
            return ResponseEntity.badRequest().body("Invalid role.");
        }
        String token = jwtService.generateToken(user);
        if (token == null) {
            return ResponseEntity.badRequest().body("Invalid credentials.");
        }
        return ResponseEntity.ok(new TokenResponseDto(token));
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyToken(@RequestBody @Valid TokenRegisterDto tokenRegisterDto) {
        boolean token = jwtService.validateToken(tokenRegisterDto.token());
        if (token) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    @GetMapping("/students")
    @PreAuthorize("hasRole('ADMINISTRATIVO') or hasRole('DOCENTE')")
    public ResponseEntity<List<UserResponseDto>> getStudents() {
        List<User> students = userService.getStudents(UserRoleEnum.ESTUDIANTE);
        List<UserResponseDto> response = students.stream().map(UserResponseDto::new).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/teachers")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<List<UserResponseDto>> getTeachers() {
        List<User> teachers = userService.getTeachers(UserRoleEnum.DOCENTE);
        List<UserResponseDto> response = teachers.stream().map(UserResponseDto::new).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getUser(@PathVariable String id) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!authenticatedUser.getId().equals(id)) {
            return ResponseEntity.badRequest().body("Invalid user.");
        }

        User user = userService.getUser(id);
        return ResponseEntity.ok(new UserResponseDto(user));
    }

    @GetMapping("/teacher/{username}")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<Object> getUserInfo(@PathVariable String username) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(authenticatedUser.getRole() == UserRoleEnum.ADMINISTRATIVO)) {
            return ResponseEntity.badRequest().body("Invalid user.");
        }

        User user = userService.getUserByUsername(username);

        if (user.getRole() == UserRoleEnum.SUDO) {
            return ResponseEntity.badRequest().body("Invalid user.");
        }

        return ResponseEntity.ok(new UserResponseDto(user));
    }

    @PutMapping("/update/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> updatePassword(@RequestBody @Valid UserUpdatePasswordDto userUpdatePasswordDto) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!authenticatedUser.getId().equals(userUpdatePasswordDto.id())) {
            return ResponseEntity.badRequest().body("Invalid user.");
        }
        userService.updatePassword(userUpdatePasswordDto.id(), userUpdatePasswordDto.password());
        return ResponseEntity.ok().build();
    }
}
