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
class DefectFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createAndListDefectEndToEndTest() throws Exception {
        long userId = 9101L;

        mockMvc.perform(post("/api/v1/defects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": %d,
                                  "coffeeDisplayName": "Cafe funcional",
                                  "coffeeRegion": "Cusco",
                                  "coffeeVariety": "Bourbon",
                                  "coffeeTotalWeight": 500.0,
                                  "name": "Astringencia",
                                  "defectType": "Sabor",
                                  "defectWeight": 5.0,
                                  "percentage": 1.0,
                                  "probableCause": "Extraccion alta",
                                  "suggestedSolution": "Ajustar molienda"
                                }
                                """.formatted(userId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value((int) userId));

        mockMvc.perform(get("/api/v1/defects/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Astringencia"));
    }

    @Test
    void returnBadRequestWhenDefectPayloadInvalidTest() throws Exception {
        mockMvc.perform(post("/api/v1/defects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": 1,
                                  "coffeeDisplayName": "Cafe funcional",
                                  "coffeeRegion": "Cusco",
                                  "coffeeVariety": "Bourbon",
                                  "coffeeTotalWeight": 500.0,
                                  "name": "Astringencia",
                                  "defectType": "Sabor",
                                  "defectWeight": 5.0,
                                  "percentage": 101.0,
                                  "probableCause": "Extraccion alta",
                                  "suggestedSolution": "Ajustar molienda"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }
}
