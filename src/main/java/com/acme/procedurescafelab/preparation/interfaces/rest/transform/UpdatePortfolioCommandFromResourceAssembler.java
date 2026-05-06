package com.acme.procedurescafelab.preparation.interfaces.rest.transform;

import com.acme.procedurescafelab.preparation.domain.model.commands.UpdatePortfolioCommand;
import com.acme.procedurescafelab.preparation.interfaces.rest.resources.UpdatePortfolioResource;

public class UpdatePortfolioCommandFromResourceAssembler {
    public static UpdatePortfolioCommand toCommandFromResource(
            Long userId, Long portfolioId, UpdatePortfolioResource resource) {
        return new UpdatePortfolioCommand(userId, portfolioId, resource.name());
    }
}
