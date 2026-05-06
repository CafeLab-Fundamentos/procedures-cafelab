package com.acme.procedurescafelab.preparation.domain.services;

import com.acme.procedurescafelab.preparation.domain.model.aggregates.Portfolio;
import com.acme.procedurescafelab.preparation.domain.model.queries.GetPortfolioByIdForUserQuery;
import com.acme.procedurescafelab.preparation.domain.model.queries.GetPortfoliosByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface PortfolioQueryService {
    List<Portfolio> handle(GetPortfoliosByUserIdQuery query);

    Optional<Portfolio> handle(GetPortfolioByIdForUserQuery query);
}
