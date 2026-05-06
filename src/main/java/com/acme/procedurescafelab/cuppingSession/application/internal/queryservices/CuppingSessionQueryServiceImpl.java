package com.acme.procedurescafelab.cuppingSession.application.internal.queryservices;

import com.acme.procedurescafelab.cuppingSession.domain.model.aggregates.CuppingSession;
import com.acme.procedurescafelab.cuppingSession.domain.model.queries.GetCuppingSessionByIdForUserQuery;
import com.acme.procedurescafelab.cuppingSession.domain.model.queries.GetCuppingSessionsByUserIdQuery;
import com.acme.procedurescafelab.cuppingSession.domain.services.CuppingSessionQueryService;
import com.acme.procedurescafelab.cuppingSession.infrastructure.persistence.jpa.repositories.CuppingSessionRepository;
import com.acme.procedurescafelab.shared.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuppingSessionQueryServiceImpl implements CuppingSessionQueryService {
    private final CuppingSessionRepository repository;

    public CuppingSessionQueryServiceImpl(CuppingSessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CuppingSession> handle(GetCuppingSessionsByUserIdQuery query) {
        return repository.findByUserIdOrderBySessionDateDescCreatedAtDesc(new UserId(query.userId()));
    }

    @Override
    public Optional<CuppingSession> handle(GetCuppingSessionByIdForUserQuery query) {
        return repository.findByIdAndUserId(query.sessionId(), new UserId(query.userId()));
    }
}
