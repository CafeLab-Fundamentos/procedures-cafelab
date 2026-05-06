package com.acme.procedurescafelab.preparation.interfaces.rest;

import com.acme.procedurescafelab.preparation.domain.model.aggregates.Recipe;
import com.acme.procedurescafelab.preparation.domain.model.commands.CreateRecipeCommand;
import com.acme.procedurescafelab.preparation.interfaces.acl.PreparationContextFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
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
class PreparationModuleIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PreparationContextFacade preparationContextFacade;

    @Test
    void createRecipeSuccessfullyTest() throws Exception {
        var recipe = new Recipe(new CreateRecipeCommand(
                1L, "V60", "img", "v60", "coffee", "1:16",
                3L, 4L, 180, "steps", "tips", "ok", "medio"));
        ReflectionTestUtils.setField(recipe, "createdAt", new Date());
        ReflectionTestUtils.setField(recipe, "updatedAt", new Date());
        when(preparationContextFacade.createRecipe(any())).thenReturn(Optional.of(recipe));
        when(preparationContextFacade.getIngredientsByRecipeId(any())).thenReturn(List.of());

        mockMvc.perform(post("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": 1,
                                  "name": "V60",
                                  "imageUrl": "img",
                                  "extractionMethod": "v60",
                                  "extractionCategory": "coffee",
                                  "ratio": "1:16",
                                  "cuppingSessionId": 3,
                                  "portfolioId": 4,
                                  "preparationTime": 180,
                                  "steps": "steps",
                                  "tips": "tips",
                                  "cupping": "ok",
                                  "grindSize": "medio"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("V60"));
    }

    @Test
    void returnBadRequestWhenRecipeCreationFailsTest() throws Exception {
        when(preparationContextFacade.createRecipe(any())).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": 1,
                                  "name": "V60",
                                  "imageUrl": "img",
                                  "extractionMethod": "v60",
                                  "extractionCategory": "coffee",
                                  "ratio": "1:16",
                                  "cuppingSessionId": 3,
                                  "portfolioId": 4,
                                  "preparationTime": 180,
                                  "steps": "steps",
                                  "tips": "tips",
                                  "cupping": "ok",
                                  "grindSize": "medio"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void listRecipesByUserTest() throws Exception {
        var recipe = new Recipe(new CreateRecipeCommand(
                1L, "V60", "img", "v60", "coffee", "1:16",
                3L, 4L, 180, "steps", "tips", "ok", "medio"));
        ReflectionTestUtils.setField(recipe, "createdAt", new Date());
        ReflectionTestUtils.setField(recipe, "updatedAt", new Date());
        when(preparationContextFacade.getRecipesByUserId(1L)).thenReturn(List.of(recipe));
        when(preparationContextFacade.getIngredientsByRecipeId(any())).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/recipes/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("V60"));
    }

    @Test
    void deleteRecipeSuccessfullyTest() throws Exception {
        when(preparationContextFacade.deleteRecipe(10L, 1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/recipes/user/1/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Receta eliminada exitosamente"));
    }
}
