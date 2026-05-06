package com.acme.procedurescafelab.cuppingSession.domain.model.aggregates;

import com.acme.procedurescafelab.cuppingSession.domain.model.commands.CreateCuppingSessionCommand;
import com.acme.procedurescafelab.cuppingSession.domain.model.commands.UpdateCuppingSessionCommand;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CuppingSessionTest {

    @Test
    void createSessionFromCommandTest() {
        var command = new CreateCuppingSessionCommand(
                4L, " Sesion A ", " Peru ", " Caturra ", " Lavado ",
                LocalDate.of(2026, 5, 1), true, "{}", " notas ");

        var session = new CuppingSession(command);

        assertEquals(4L, session.getUserId().userId());
        assertEquals("Sesion A", session.getName());
        assertEquals("Peru", session.getOrigin());
        assertTrue(session.isFavorite());
    }

    @Test
    void normalizeBlankStringsToNullOnCreateTest() {
        var session = new CuppingSession(new CreateCuppingSessionCommand(
                4L, "N", "O", "V", "P", LocalDate.of(2026, 5, 1), false, " ", ""));

        assertNull(session.getResultsJson());
        assertNull(session.getRoastStyleNotes());
    }

    @Test
    void applyUpdateValuesTest() {
        var session = new CuppingSession(new CreateCuppingSessionCommand(
                4L, "N", "O", "V", "P", LocalDate.of(2026, 5, 1), false, "{}", "x"));

        session.applyUpdate(new UpdateCuppingSessionCommand(
                2L, 4L, " Nuevo ", " Jaen ", " Bourb ", " Honey ",
                LocalDate.of(2026, 6, 1), true, " ", " nueva "));

        assertEquals("Nuevo", session.getName());
        assertEquals("Jaen", session.getOrigin());
        assertTrue(session.isFavorite());
        assertNull(session.getResultsJson());
        assertEquals("nueva", session.getRoastStyleNotes());
    }

    @Test
    void exposeNullIdBeforePersistenceTest() {
        assertNull(new CuppingSession().getId());
    }
}
