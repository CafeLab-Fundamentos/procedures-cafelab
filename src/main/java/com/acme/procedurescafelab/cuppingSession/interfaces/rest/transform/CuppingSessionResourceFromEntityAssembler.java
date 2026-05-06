package com.acme.procedurescafelab.cuppingSession.interfaces.rest.transform;

import com.acme.procedurescafelab.cuppingSession.domain.model.aggregates.CuppingSession;
import com.acme.procedurescafelab.cuppingSession.interfaces.rest.resources.CuppingSessionResource;

public class CuppingSessionResourceFromEntityAssembler {

    public static CuppingSessionResource toResource(CuppingSession e) {
        return new CuppingSessionResource(
                e.getId(),
                e.getUserId().userId(),
                e.getName(),
                e.getOrigin(),
                e.getVariety(),
                e.getProcessing(),
                e.getSessionDate(),
                e.isFavorite(),
                e.getResultsJson(),
                e.getRoastStyleNotes());
    }
}
