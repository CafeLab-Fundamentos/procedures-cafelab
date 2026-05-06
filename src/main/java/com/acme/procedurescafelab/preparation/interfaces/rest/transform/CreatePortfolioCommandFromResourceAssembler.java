package com.acme.procedurescafelab.preparation.interfaces.rest.transform;

import com.acme.procedurescafelab.preparation.domain.model.commands.CreatePortfolioCommand;
import com.acme.procedurescafelab.preparation.interfaces.rest.resources.CreatePortfolioResource;

public class CreatePortfolioCommandFromResourceAssembler {
    public static CreatePortfolioCommand toCommand(CreatePortfolioResource resource) {
        return new CreatePortfolioCommand(resource.userId(), resource.name());
    }
}
