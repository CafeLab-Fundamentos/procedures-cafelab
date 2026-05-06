package com.acme.procedurescafelab.preparation.domain.model.aggregates;

import com.acme.procedurescafelab.preparation.domain.model.commands.CreateRecipeCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.UpdateRecipeCommand;
import com.acme.procedurescafelab.preparation.domain.model.valueobjects.ExtractionCategory;
import com.acme.procedurescafelab.preparation.domain.model.valueobjects.ExtractionMethod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class RecipeTest {

    @Test
    void createRecipeFromCommandTest() {
        var recipe = new Recipe(new CreateRecipeCommand(
                1L, "V60", "img", "v60", "coffee", "1:16",
                3L, 4L, 180, "steps", "tips", "ok", "medio"));

        assertEquals(1L, recipe.getUserId().userId());
        assertEquals("V60", recipe.getName());
        assertEquals(ExtractionMethod.V60, recipe.getExtractionMethod());
        assertEquals(ExtractionCategory.COFFEE, recipe.getExtractionCategory());
    }

    @Test
    void createRecipeFromPrimitiveConstructorTest() {
        var recipe = new Recipe(
                2L, "Espresso", "img", "espresso", "espresso",
                "1:2", null, null, 30, "steps", null, "ok", "fino");

        assertEquals(2L, recipe.getUserId().userId());
        assertEquals("Espresso", recipe.getName());
        assertEquals("1:2", recipe.getRatio());
    }

    @Test
    void updateRecipeAndReturnSameInstanceTest() {
        var recipe = new Recipe(new CreateRecipeCommand(
                1L, "V60", "img", "v60", "coffee", "1:16",
                3L, 4L, 180, "steps", "tips", "ok", "medio"));

        var same = recipe.update(new UpdateRecipeCommand(
                1L, 10L, "Chemex", "img2", "chemex", "coffee",
                "1:15", 5L, 6L, 200, "steps2", "tips2", "great", "grueso"));

        assertSame(recipe, same);
        assertEquals("Chemex", recipe.getName());
        assertEquals(ExtractionMethod.CHEMEX, recipe.getExtractionMethod());
        assertEquals(5L, recipe.getCuppingSessionId());
    }
}
