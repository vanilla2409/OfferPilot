package com.offerpilot.controller;

import com.offerpilot.model.CodingQuestion;
import com.offerpilot.repository.CodingQuestionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class CodingQuestionController {

    private final CodingQuestionRepository questionRepository;

    public CodingQuestionController(CodingQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping
    public ResponseEntity<List<CodingQuestion>> getAllQuestions(
            @RequestParam(required = false) CodingQuestion.Difficulty difficulty,
            @RequestParam(required = false) String topic) {

        if (difficulty != null && topic != null) {
            return ResponseEntity.ok(questionRepository.findByDifficultyAndTopic(difficulty, topic));
        } else if (difficulty != null) {
            return ResponseEntity.ok(questionRepository.findByDifficulty(difficulty));
        } else if (topic != null) {
            return ResponseEntity.ok(questionRepository.findByTopic(topic));
        } else {
            return ResponseEntity.ok(questionRepository.findAll());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CodingQuestion> getQuestion(@PathVariable Long id) {
        return questionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
