package com.acme.procedurescafelab.defect.interfaces.rest;

import com.acme.procedurescafelab.defect.domain.model.aggregates.Defect;
import com.acme.procedurescafelab.defect.domain.model.commands.CreateDefectCommand;
import com.acme.procedurescafelab.defect.domain.model.queries.GetDefectsByUserIdQuery;
import com.acme.procedurescafelab.defect.domain.services.DefectCommandService;
import com.acme.procedurescafelab.defect.domain.services.DefectQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class DefectsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DefectCommandService defectCommandService;

    @MockBean
    private DefectQueryService defectQueryService;

    @Test
    void createDefectSuccessfullyTest() throws Exception {
        var defect = new Defect(new CreateDefectCommand(
                1L, "Cafe A", "Cusco", "Bourbon", 500.0,
                "Sobre extraccion", "Molienda", 5.0, 1.0, "Molido fino", "Reducir molienda"));

        when(defectCommandService.handle(any(CreateDefectCommand.class))).thenReturn(Optional.of(defect));

        mockMvc.perform(post("/api/v1/defects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": 1,
                                  "coffeeDisplayName": "Cafe A",
                                  "coffeeRegion": "Cusco",
                                  "coffeeVariety": "Bourbon",
                                  "coffeeTotalWeight": 500,
                                  "name": "Sobre extraccion",
                                  "defectType": "Molienda",
                                  "defectWeight": 5,
                                  "percentage": 1,
                                  "probableCause": "Molido fino",
                                  "suggestedSolution": "Reducir molienda"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    void returnBadRequestWhenCreationFailsTest() throws Exception {
        when(defectCommandService.handle(any(CreateDefectCommand.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/defects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": 1,
                                  "coffeeDisplayName": "Cafe A",
                                  "coffeeRegion": "Cusco",
                                  "coffeeVariety": "Bourbon",
                                  "coffeeTotalWeight": 500,
                                  "name": "Sobre extraccion",
                                  "defectType": "Molienda",
                                  "defectWeight": 5,
                                  "percentage": 1,
                                  "probableCause": "Molido fino",
                                  "suggestedSolution": "Reducir molienda"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void listDefectsByUserIdTest() throws Exception {
        var defect = new Defect(new CreateDefectCommand(
                1L, "Cafe A", "Cusco", "Bourbon", 500.0,
                "Sobre extraccion", "Molienda", 5.0, 1.0, "Molido fino", "Reducir molienda"));
        when(defectQueryService.handle(any(GetDefectsByUserIdQuery.class))).thenReturn(List.of(defect));

        mockMvc.perform(get("/api/v1/defects/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Sobre extraccion"));
    }
}
