package com.acme.procedurescafelab.functional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PreparationFunctionalTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createAndListRecipeEndToEndTest() throws Exception {
        long userId = 9401L;

        mockMvc.perform(post("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": %d,
                                  "name": "Receta funcional",
                                  "imageUrl": "img",
                                  "extractionMethod": "v60",
                                  "extractionCategory": "coffee",
                                  "ratio": "1:16",
                                  "cuppingSessionId": null,
                                  "portfolioId": null,
                                  "preparationTime": 180,
                                  "steps": "Pasos",
                                  "tips": "Tips",
                                  "cupping": "ok",
                                  "grindSize": "medio"
                                }
                                """.formatted(userId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Receta funcional"));

        mockMvc.perform(get("/api/v1/recipes/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Receta funcional"));
    }

    @Test
    void returnBadRequestForInvalidRecipePayloadTest() throws Exception {
        mockMvc.perform(post("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": 9401,
                                  "name": "",
                                  "imageUrl": "img",
                                  "extractionMethod": "v60",
                                  "extractionCategory": "coffee",
                                  "ratio": "1:16",
                                  "cuppingSessionId": null,
                                  "portfolioId": null,
                                  "preparationTime": 180,
                                  "steps": "Pasos",
                                  "tips": "Tips",
                                  "cupping": "ok",
                                  "grindSize": "medio"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void returnNotFoundForMissingRecipeTest() throws Exception {
        mockMvc.perform(get("/api/v1/recipes/user/{userId}/{recipeId}", 9401L, 999999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void createAndListPortfolioTest() throws Exception {
        long userId = 9402L;

        mockMvc.perform(post("/api/v1/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": %d,
                                  "name": "Portafolio funcional"
                                }
                                """.formatted(userId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Portafolio funcional"));

        mockMvc.perform(get("/api/v1/portfolios/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Portafolio funcional"));
    }

    @Test
    void returnNotFoundForMissingPortfolioTest() throws Exception {
        mockMvc.perform(get("/api/v1/portfolios/user/{userId}/{portfolioId}", 9402L, 999999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void addAndListIngredientForRecipeTest() throws Exception {
        long userId = 9403L;
        long recipeId = createRecipe(userId, "RecetaIng");

        mockMvc.perform(post("/api/v1/recipes/{recipeId}/ingredients", recipeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "recipeId": %d,
                                  "name": "Agua",
                                  "amount": 250.0,
                                  "unit": "ml"
                                }
                                """.formatted(recipeId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Agua"));

        mockMvc.perform(get("/api/v1/recipes/{recipeId}/ingredients", recipeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Agua"));
    }

    private long createRecipe(long userId, String name) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": %d,
                                  "name": "%s",
                                  "imageUrl": "img",
                                  "extractionMethod": "v60",
                                  "extractionCategory": "coffee",
                                  "ratio": "1:16",
                                  "cuppingSessionId": null,
                                  "portfolioId": null,
                                  "preparationTime": 180,
                                  "steps": "Pasos",
                                  "tips": "Tips",
                                  "cupping": "ok",
                                  "grindSize": "medio"
                                }
                                """.formatted(userId, name)))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode body = objectMapper.readTree(result.getResponse().getContentAsString());
        return body.get("id").asLong();
    }
}
