package com.acme.procedurescafelab.cuppingSession.interfaces.rest.transform;

import com.acme.procedurescafelab.cuppingSession.domain.model.commands.CreateCuppingSessionCommand;
import com.acme.procedurescafelab.cuppingSession.interfaces.rest.resources.CreateCuppingSessionResource;

public class CreateCuppingSessionCommandFromResourceAssembler {

    public static CreateCuppingSessionCommand toCommand(CreateCuppingSessionResource r) {
        return new CreateCuppingSessionCommand(
                r.userId(),
                r.name(),
                r.origin(),
                r.variety(),
                r.processing(),
                r.sessionDate(),
                r.favoriteOrDefault(),
                r.resultsJson(),
                r.roastStyleNotes());
    }
}
