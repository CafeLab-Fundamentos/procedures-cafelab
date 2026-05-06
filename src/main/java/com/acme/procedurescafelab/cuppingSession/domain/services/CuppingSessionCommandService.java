package com.acme.procedurescafelab.cuppingSession.domain.services;

import com.acme.procedurescafelab.cuppingSession.domain.model.aggregates.CuppingSession;
import com.acme.procedurescafelab.cuppingSession.domain.model.commands.CreateCuppingSessionCommand;
import com.acme.procedurescafelab.cuppingSession.domain.model.commands.UpdateCuppingSessionCommand;

import java.util.Optional;

public interface CuppingSessionCommandService {

    Optional<CuppingSession> handle(CreateCuppingSessionCommand command);

    Optional<CuppingSession> handle(UpdateCuppingSessionCommand command);

    boolean delete(Long sessionId, Long userId);
}
