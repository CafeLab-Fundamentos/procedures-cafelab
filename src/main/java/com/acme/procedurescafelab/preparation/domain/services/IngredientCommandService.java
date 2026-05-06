package com.acme.procedurescafelab.preparation.domain.services;

import com.acme.procedurescafelab.preparation.domain.model.aggregates.Ingredient;
import com.acme.procedurescafelab.preparation.domain.model.commands.CreateIngredientCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.UpdateIngredientCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.DeleteIngredientCommand;

import java.util.Optional;

public interface IngredientCommandService {
    Optional<Ingredient> handle(CreateIngredientCommand command);
    Optional<Ingredient> handle(UpdateIngredientCommand command);
    boolean handle(DeleteIngredientCommand command);
}
