package com.acme.procedurescafelab.preparation.interfaces.rest.transform;

import com.acme.procedurescafelab.preparation.domain.model.commands.CreateRecipeCommand;
import com.acme.procedurescafelab.preparation.interfaces.rest.resources.CreateRecipeResource;

public class CreateRecipeCommandFromResourceAssembler {

    public static CreateRecipeCommand toCommand(CreateRecipeResource resource) {
        return new CreateRecipeCommand(
                resource.userId(),
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
                resource.grindSize());
    }
}
