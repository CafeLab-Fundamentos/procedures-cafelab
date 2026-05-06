package com.acme.procedurescafelab.preparation.interfaces.rest.transform;

import com.acme.procedurescafelab.preparation.domain.model.commands.UpdateRecipeCommand;
import com.acme.procedurescafelab.preparation.interfaces.rest.resources.UpdateRecipeResource;

public class UpdateRecipeCommandFromResourceAssembler {
    public static UpdateRecipeCommand toCommandFromResource(
            Long userId, Long recipeId, UpdateRecipeResource resource) {
        return new UpdateRecipeCommand(
            userId,
            recipeId,
            resource.name(),
            resource.imageUrl(),
            resource.extractionMethod(),
            resource.extractionCategory(),
            resource.ratio(),
            resource.cuppingSessionId(),
            resource.portfolioId(),
            resource.preparationTime(),
            resource.steps(),
            resource.tips(),
            resource.cupping(),
            resource.grindSize()
        );
    }
}
