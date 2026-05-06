package com.acme.procedurescafelab.preparation.interfaces.rest.transform;

import com.acme.procedurescafelab.preparation.domain.model.commands.CreateIngredientCommand;
import com.acme.procedurescafelab.preparation.interfaces.rest.resources.CreateIngredientResource;

public class CreateIngredientCommandFromResourceAssembler {
    public static CreateIngredientCommand toCommandFromResource(CreateIngredientResource resource) {
        return new CreateIngredientCommand(
            resource.recipeId(),
            resource.name(),
            resource.amount(),
            resource.unit()
        );
    }
}
