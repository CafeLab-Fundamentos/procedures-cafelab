package com.acme.procedurescafelab.preparation.interfaces.rest.transform;

import com.acme.procedurescafelab.preparation.domain.model.commands.UpdateIngredientCommand;
import com.acme.procedurescafelab.preparation.interfaces.rest.resources.UpdateIngredientResource;

public class UpdateIngredientCommandFromResourceAssembler {
    public static UpdateIngredientCommand toCommandFromResource(Long ingredientId, UpdateIngredientResource resource) {
        return new UpdateIngredientCommand(
            ingredientId,
            resource.name(),
            resource.amount(),
            resource.unit()
        );
    }
}
