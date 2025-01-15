package com.dj.v_02.asign;

import com.dj.v_02.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/asign")
public class AsignController {

    private final AsignService asignService;

    public AsignController(AsignService asignService) {
        this.asignService = asignService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<AsignResponseDto> create(@RequestBody @Valid AsignRegisterDto asignRegisterDto, UriComponentsBuilder uriComponentsBuilder) {
        if (asignRegisterDto == null) {
            throw new EntityNotFoundException("The request body cannot be empty.");
        }
        Asign asign = asignService.createAsign(asignRegisterDto);
        AsignResponseDto asignResponseDto = new AsignResponseDto(asign);
        URI url = uriComponentsBuilder.path("/asign/{id}").buildAndExpand(asign.getId()).toUri();
        return ResponseEntity.created(url).body(asignResponseDto);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<List<AsignResponseDto>> findAll() {
        List<Asign> asigns = asignService.findAll();
        List<AsignResponseDto> response = asigns.stream().map(AsignResponseDto::new).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list/{userId}")
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<List<AsignResponseDto>> findByUserId(@PathVariable String userId) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!authenticatedUser.getId().equals(userId)) {
            throw new EntityNotFoundException("User not found");
        }

        List<Asign> asigns = asignService.findByUserId(userId);
        List<AsignResponseDto> response = asigns.stream().map(AsignResponseDto::new).toList();
        return ResponseEntity.ok(response);
    }
}
