package com.acme.procedurescafelab.cuppingSession.infrastructure.persistence.jpa.repositories;

import com.acme.procedurescafelab.cuppingSession.domain.model.aggregates.CuppingSession;
import com.acme.procedurescafelab.shared.domain.model.valueobjects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuppingSessionRepository extends JpaRepository<CuppingSession, Long> {

    List<CuppingSession> findByUserIdOrderBySessionDateDescCreatedAtDesc(UserId userId);

    Optional<CuppingSession> findByIdAndUserId(Long id, UserId userId);
}
