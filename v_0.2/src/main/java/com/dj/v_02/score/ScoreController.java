package com.dj.v_02.score;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/scores")
public class ScoreController {
    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<ScoreResponseDto> createScore(@RequestBody @Valid ScoreRegisterDto scoreRegisterDto, UriComponentsBuilder uriComponentsBuilder) {
        Score score = scoreService.createScore(scoreRegisterDto);
        ScoreResponseDto scoreResponseDto = new ScoreResponseDto(score);
        URI url = uriComponentsBuilder.path("/scores/{id}").buildAndExpand(score.getId()).toUri();
        return ResponseEntity.created(url).body(scoreResponseDto);
    }
}
