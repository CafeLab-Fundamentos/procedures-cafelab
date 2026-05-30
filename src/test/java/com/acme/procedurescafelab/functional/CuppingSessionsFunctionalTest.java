package com.acme.procedurescafelab.functional;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CuppingSessionsFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    void updateResultsJsonAndListCuppingSessionsTest() throws Exception {
        long userId = 9302L;
        String resultsJson =
                "{\"aroma\":5,\"cuerpo\":5,\"acidez\":5,\"dulzor\":5,\"amargor\":5,\"aftertaste\":5}";

        var createResult =
                mockMvc.perform(post("/api/v1/cupping-sessions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "userId": %d,
                                          "name": "Sesion antes de cata",
                                          "origin": "Chile",
                                          "variety": "Bourbon",
                                          "processing": "washed",
                                          "sessionDate": "2026-05-29",
                                          "favorite": false
                                        }
                                        """.formatted(userId)))
                        .andExpect(status().isCreated())
                        .andReturn();

        long sessionId =
                objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(put("/api/v1/cupping-sessions/user/{userId}/{sessionId}", userId, sessionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Sesion del user %d",
                                  "origin": "Chile",
                                  "variety": "Bourbon",
                                  "processing": "washed",
                                  "sessionDate": "2026-05-29",
                                  "favorite": false,
                                  "resultsJson": "%s"
                                }
                                """.formatted(userId, resultsJson.replace("\"", "\\\""))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultsJson").value(resultsJson));

        mockMvc.perform(get("/api/v1/cupping-sessions/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value((int) sessionId))
                .andExpect(jsonPath("$[0].resultsJson").value(resultsJson));
    }

    @Test
    void returnNotFoundWhenDeletingMissingSessionTest() throws Exception {
        mockMvc.perform(delete("/api/v1/cupping-sessions/user/{userId}/{sessionId}", 9301L, 999999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }
}
