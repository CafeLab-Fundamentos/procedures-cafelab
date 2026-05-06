package com.acme.procedurescafelab.cuppingSession.interfaces.rest.transform;

import com.acme.procedurescafelab.cuppingSession.domain.model.commands.UpdateCuppingSessionCommand;
import com.acme.procedurescafelab.cuppingSession.interfaces.rest.resources.UpdateCuppingSessionResource;

public class UpdateCuppingSessionCommandFromResourceAssembler {

    public static UpdateCuppingSessionCommand toCommand(
            Long sessionId, Long userId, UpdateCuppingSessionResource r) {
        return new UpdateCuppingSessionCommand(
                sessionId,
                userId,
                r.name(),
                r.origin(),
                r.variety(),
                r.processing(),
                r.sessionDate(),
                r.favorite(),
                r.resultsJson(),
                r.roastStyleNotes());
    }
}
