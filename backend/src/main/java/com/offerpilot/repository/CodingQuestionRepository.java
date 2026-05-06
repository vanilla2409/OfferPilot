package com.offerpilot.repository;

import com.offerpilot.model.CodingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CodingQuestionRepository extends JpaRepository<CodingQuestion, Long> {
    List<CodingQuestion> findByDifficulty(CodingQuestion.Difficulty difficulty);
    List<CodingQuestion> findByTopic(String topic);
    List<CodingQuestion> findByDifficultyAndTopic(CodingQuestion.Difficulty difficulty, String topic);
    List<CodingQuestion> findByTagsContaining(String tag);
}
