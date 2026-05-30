package com.acme.procedurescafelab.cuppingSession.interfaces.rest.transform;

import com.acme.procedurescafelab.cuppingSession.domain.model.aggregates.CuppingSession;
import com.acme.procedurescafelab.cuppingSession.interfaces.rest.resources.CuppingSessionResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CuppingSessionResourceFromEntityAssembler {

    private static final Logger log = LoggerFactory.getLogger(CuppingSessionResourceFromEntityAssembler.class);

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
                safeResultsJson(e),
                e.getRoastStyleNotes());
    }

    static String safeResultsJson(CuppingSession session) {
        try {
            String raw = session.getResultsJson();
            if (raw == null || raw.isBlank()) {
                return null;
            }
            return raw.trim();
        } catch (RuntimeException ex) {
            log.warn(
                    "No se pudo leer resultsJson de la sesion id={}: {}",
                    session.getId(),
                    ex.getMessage());
            return null;
        }
    }
}
