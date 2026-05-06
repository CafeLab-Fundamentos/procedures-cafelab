package com.acme.procedurescafelab.cuppingSession.domain.services;

import com.acme.procedurescafelab.cuppingSession.domain.model.aggregates.CuppingSession;
import com.acme.procedurescafelab.cuppingSession.domain.model.queries.GetCuppingSessionByIdForUserQuery;
import com.acme.procedurescafelab.cuppingSession.domain.model.queries.GetCuppingSessionsByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface CuppingSessionQueryService {

    List<CuppingSession> handle(GetCuppingSessionsByUserIdQuery query);

    Optional<CuppingSession> handle(GetCuppingSessionByIdForUserQuery query);
}
