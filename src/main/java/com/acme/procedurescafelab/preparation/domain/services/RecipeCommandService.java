package com.acme.procedurescafelab.preparation.domain.services;

import com.acme.procedurescafelab.preparation.domain.model.aggregates.Recipe;
import com.acme.procedurescafelab.preparation.domain.model.commands.CreateRecipeCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.UpdateRecipeCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.DeleteRecipeCommand;

import java.util.Optional;

public interface RecipeCommandService {
    Optional<Recipe> handle(CreateRecipeCommand command);
    Optional<Recipe> handle(UpdateRecipeCommand command);
    boolean handle(DeleteRecipeCommand command);
}
