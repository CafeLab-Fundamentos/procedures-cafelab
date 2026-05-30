package com.acme.procedurescafelab.cuppingSession.interfaces.rest.transform;

import com.acme.procedurescafelab.cuppingSession.domain.model.aggregates.CuppingSession;
import com.acme.procedurescafelab.cuppingSession.domain.model.commands.CreateCuppingSessionCommand;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CuppingSessionResourceFromEntityAssemblerTest {

    @Test
    void mapsResultsJsonAsPlainStringTest() {
        String json = "{\"aroma\":5,\"cuerpo\":5}";
        var session = new CuppingSession(new CreateCuppingSessionCommand(
                8L, "Sesion", "Chile", "Bourbon", "washed",
                LocalDate.of(2026, 5, 29), false, json, null));

        var resource = CuppingSessionResourceFromEntityAssembler.toResource(session);

        assertEquals(json, resource.resultsJson());
    }

    @Test
    void keepsInvalidJsonAsStringWithoutParsingTest() {
        String invalidJson = "{not-valid-json";
        var session = new CuppingSession(new CreateCuppingSessionCommand(
                8L, "Sesion", "Chile", "Bourbon", "washed",
                LocalDate.of(2026, 5, 29), false, invalidJson, null));

        assertEquals(invalidJson, CuppingSessionResourceFromEntityAssembler.safeResultsJson(session));
    }

    @Test
    void blankResultsJsonBecomesNullTest() {
        var session = new CuppingSession(new CreateCuppingSessionCommand(
                8L, "Sesion", "Chile", "Bourbon", "washed",
                LocalDate.of(2026, 5, 29), false, "   ", null));

        assertNull(CuppingSessionResourceFromEntityAssembler.safeResultsJson(session));
    }
}
