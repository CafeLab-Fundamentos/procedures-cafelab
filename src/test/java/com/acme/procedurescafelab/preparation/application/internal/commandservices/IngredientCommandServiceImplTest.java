package com.acme.procedurescafelab.preparation.application.internal.commandservices;

import com.acme.procedurescafelab.preparation.domain.model.aggregates.Ingredient;
import com.acme.procedurescafelab.preparation.domain.model.commands.DeleteIngredientCommand;
import com.acme.procedurescafelab.preparation.infrastructure.persistence.jpa.repositories.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientCommandServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientCommandServiceImpl ingredientCommandService;

    @Test
    void deleteIngredientByRecipeAndIdTest() {
        var ingredient = new Ingredient(6L, "Agua", 250.0, "ml");
        when(ingredientRepository.findByIdAndRecipeId(7L, 6L)).thenReturn(Optional.of(ingredient));

        var deleted = ingredientCommandService.handle(new DeleteIngredientCommand(6L, 7L));

        assertTrue(deleted);
        verify(ingredientRepository).delete(ingredient);
    }

    @Test
    void returnFalseWhenIngredientNotInRecipeTest() {
        when(ingredientRepository.findByIdAndRecipeId(7L, 6L)).thenReturn(Optional.empty());

        var deleted = ingredientCommandService.handle(new DeleteIngredientCommand(6L, 7L));

        assertFalse(deleted);
    }
}
