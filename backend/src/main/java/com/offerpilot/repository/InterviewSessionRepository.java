package com.offerpilot.repository;

import com.offerpilot.model.InterviewSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InterviewSessionRepository extends JpaRepository<InterviewSession, Long> {
    List<InterviewSession> findByUserIdOrderByStartedAtDesc(Long userId);
    List<InterviewSession> findByUserIdAndSessionType(Long userId, InterviewSession.SessionType type);
    long countByUserIdAndStatus(Long userId, InterviewSession.Status status);
}
