package com.acme.procedurescafelab.functional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CalibrationsFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createAndListCalibrationEndToEndTest() throws Exception {
        long userId = 9201L;

        mockMvc.perform(post("/api/v1/calibrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": %d,
                                  "name": "Calibracion funcional",
                                  "method": "v60",
                                  "equipment": "Comandante",
                                  "grindNumber": "15",
                                  "aperture": 1.1,
                                  "cupVolume": 200.0,
                                  "finalVolume": 180.0,
                                  "calibrationDate": "2026-05-05",
                                  "comments": "ok",
                                  "notes": "ok",
                                  "sampleImage": null
                                }
                                """.formatted(userId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value((int) userId));

        mockMvc.perform(get("/api/v1/calibrations/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Calibracion funcional"));
    }

    @Test
    void returnNotFoundForMissingCalibrationTest() throws Exception {
        mockMvc.perform(get("/api/v1/calibrations/user/{userId}/{calibrationId}", 9201L, 999999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }
}
