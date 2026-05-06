package com.acme.procedurescafelab.defect.domain.model.aggregates;

import com.acme.procedurescafelab.defect.domain.model.commands.CreateDefectCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DefectTest {

    @Test
    void createDefectFromCommandTest() {
        var command = new CreateDefectCommand(
                7L, " Typica ", "Cusco", "Typica", 500.0,
                "Sobre extraccion", "Molienda", 12.0, 2.4, "Molienda fina", "Ajustar molienda");

        var defect = new Defect(command);

        assertEquals(7L, defect.getUserId().userId());
        assertEquals("Typica", defect.getCoffeeDisplayName());
        assertEquals("Sobre extraccion", defect.getName());
        assertEquals(2.4, defect.getPercentage());
    }

    @Test
    void keepNullableCauseAndSolutionTest() {
        var command = new CreateDefectCommand(
                10L, "Catuai", "Jaen", "Catuai", 300.0,
                "Astringente", "Tostado", 6.0, 2.0, null, null);

        var defect = new Defect(command);

        assertNull(defect.getProbableCause());
        assertNull(defect.getSuggestedSolution());
    }

    @Test
    void allowMutatingFieldsViaSettersTest() {
        var defect = new Defect();
        defect.setName("Acidez alta");
        defect.setDefectWeight(1.5);
        defect.setPercentage(0.5);

        assertEquals("Acidez alta", defect.getName());
        assertEquals(1.5, defect.getDefectWeight());
        assertEquals(0.5, defect.getPercentage());
    }

    @Test
    void exposeNullIdBeforePersistenceTest() {
        assertNull(new Defect().getId());
    }
}
