package com.acme.procedurescafelab.functional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CuppingSessionsFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createAndListCuppingSessionEndToEndTest() throws Exception {
        long userId = 9301L;

        mockMvc.perform(post("/api/v1/cupping-sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": %d,
                                  "name": "Sesion funcional",
                                  "origin": "Peru",
                                  "variety": "Geisha",
                                  "processing": "Lavado",
                                  "sessionDate": "2026-05-05",
                                  "favorite": true,
                                  "resultsJson": "{}",
                                  "roastStyleNotes": "Notas funcionales"
                                }
                                """.formatted(userId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value((int) userId));

        mockMvc.perform(get("/api/v1/cupping-sessions/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Sesion funcional"));
    }

    @Test
    void returnNotFoundWhenDeletingMissingSessionTest() throws Exception {
        mockMvc.perform(delete("/api/v1/cupping-sessions/user/{userId}/{sessionId}", 9301L, 999999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }
}
