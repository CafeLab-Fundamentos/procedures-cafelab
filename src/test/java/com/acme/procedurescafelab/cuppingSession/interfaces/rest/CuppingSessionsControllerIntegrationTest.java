package com.acme.procedurescafelab.cuppingSession.interfaces.rest;

import com.acme.procedurescafelab.cuppingSession.domain.model.aggregates.CuppingSession;
import com.acme.procedurescafelab.cuppingSession.domain.model.commands.CreateCuppingSessionCommand;
import com.acme.procedurescafelab.cuppingSession.domain.model.queries.GetCuppingSessionsByUserIdQuery;
import com.acme.procedurescafelab.cuppingSession.domain.services.CuppingSessionCommandService;
import com.acme.procedurescafelab.cuppingSession.domain.services.CuppingSessionQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CuppingSessionsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuppingSessionCommandService commandService;

    @MockBean
    private CuppingSessionQueryService queryService;

    @Test
    void createCuppingSessionTest() throws Exception {
        var session = new CuppingSession(new CreateCuppingSessionCommand(
                1L, "Sesion", "Peru", "Bourbon", "Lavado",
                LocalDate.of(2026, 5, 5), true, "{}", "notas"));
        when(commandService.handle(any(CreateCuppingSessionCommand.class))).thenReturn(Optional.of(session));

        mockMvc.perform(post("/api/v1/cupping-sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": 1,
                                  "name": "Sesion",
                                  "origin": "Peru",
                                  "variety": "Bourbon",
                                  "processing": "Lavado",
                                  "sessionDate": "2026-05-05",
                                  "favorite": true,
                                  "resultsJson": "{}",
                                  "roastStyleNotes": "notas"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Sesion"));
    }

    @Test
    void listCuppingSessionsByUserTest() throws Exception {
        var session = new CuppingSession(new CreateCuppingSessionCommand(
                1L, "Sesion", "Peru", "Bourbon", "Lavado",
                LocalDate.of(2026, 5, 5), true, "{}", "notas"));
        when(queryService.handle(any(GetCuppingSessionsByUserIdQuery.class))).thenReturn(List.of(session));

        mockMvc.perform(get("/api/v1/cupping-sessions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].origin").value("Peru"));
    }

    @Test
    void deleteSessionWhenExistsTest() throws Exception {
        when(commandService.delete(10L, 1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/cupping-sessions/user/1/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
