package com.offerpilot.repository;

import com.offerpilot.model.SessionFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SessionFeedbackRepository extends JpaRepository<SessionFeedback, Long> {
    Optional<SessionFeedback> findBySessionId(Long sessionId);
}
