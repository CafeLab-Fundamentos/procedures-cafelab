package com.acme.procedurescafelab.cuppingSession.application.internal.commandservices;

import com.acme.procedurescafelab.cuppingSession.domain.model.aggregates.CuppingSession;
import com.acme.procedurescafelab.cuppingSession.domain.model.commands.CreateCuppingSessionCommand;
import com.acme.procedurescafelab.cuppingSession.domain.model.commands.UpdateCuppingSessionCommand;
import com.acme.procedurescafelab.cuppingSession.domain.services.CuppingSessionCommandService;
import com.acme.procedurescafelab.cuppingSession.infrastructure.persistence.jpa.repositories.CuppingSessionRepository;
import com.acme.procedurescafelab.shared.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CuppingSessionCommandServiceImpl implements CuppingSessionCommandService {
    private final CuppingSessionRepository repository;

    public CuppingSessionCommandServiceImpl(CuppingSessionRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Optional<CuppingSession> handle(CreateCuppingSessionCommand command) {
        return Optional.of(repository.save(new CuppingSession(command)));
    }

    @Override
    @Transactional
    public Optional<CuppingSession> handle(UpdateCuppingSessionCommand command) {
        return repository
                .findByIdAndUserId(command.sessionId(), new UserId(command.userId()))
                .map(
                        entity -> {
                            entity.applyUpdate(command);
                            return repository.save(entity);
                        });
    }

    @Override
    @Transactional
    public boolean delete(Long sessionId, Long userId) {
        return repository
                .findByIdAndUserId(sessionId, new UserId(userId))
                .map(
                        entity -> {
                            repository.delete(entity);
                            return true;
                        })
                .orElse(false);
    }
}
