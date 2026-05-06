package com.acme.procedurescafelab.preparation.domain.services;

import com.acme.procedurescafelab.preparation.domain.model.commands.CreatePortfolioCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.UpdatePortfolioCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.DeletePortfolioCommand;
import com.acme.procedurescafelab.preparation.domain.model.aggregates.Portfolio;

import java.util.Optional;

public interface PortfolioCommandService {
    Long handle(CreatePortfolioCommand command);
    Optional<Portfolio> handle(UpdatePortfolioCommand command);
    boolean handle(DeletePortfolioCommand command);
}
