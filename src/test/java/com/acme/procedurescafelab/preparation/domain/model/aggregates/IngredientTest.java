package com.acme.procedurescafelab.preparation.domain.model.aggregates;

import com.acme.procedurescafelab.preparation.domain.model.commands.CreateIngredientCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.UpdateIngredientCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class IngredientTest {

    @Test
    void createIngredientFromPrimitiveConstructorTest() {
        var ingredient = new Ingredient(3L, "Agua", 250.0, "ml");

        assertEquals(3L, ingredient.getRecipeId());
        assertEquals("Agua", ingredient.getName());
        assertEquals(250.0, ingredient.getAmount());
    }

    @Test
    void createIngredientFromCommandTest() {
        var ingredient = new Ingredient(new CreateIngredientCommand(3L, "Cafe", 15.0, "g"));

        assertEquals(3L, ingredient.getRecipeId());
        assertEquals("Cafe", ingredient.getName());
        assertEquals("g", ingredient.getUnit());
    }

    @Test
    void updateIngredientAndReturnSameInstanceTest() {
        var ingredient = new Ingredient(3L, "Cafe", 15.0, "g");

        var same = ingredient.update(new UpdateIngredientCommand(99L, "Agua", 230.0, "ml"));

        assertSame(ingredient, same);
        assertEquals("Agua", ingredient.getName());
        assertEquals(230.0, ingredient.getAmount());
        assertEquals("ml", ingredient.getUnit());
    }

    @Test
    void exposeNullIdBeforePersistenceTest() {
        assertNull(new Ingredient().getId());
    }
}
