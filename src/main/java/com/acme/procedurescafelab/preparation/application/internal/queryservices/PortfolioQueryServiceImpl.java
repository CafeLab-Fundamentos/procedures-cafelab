package com.acme.procedurescafelab.preparation.application.internal.queryservices;

import com.acme.procedurescafelab.preparation.domain.model.aggregates.Portfolio;
import com.acme.procedurescafelab.preparation.domain.model.queries.GetPortfolioByIdForUserQuery;
import com.acme.procedurescafelab.preparation.domain.model.queries.GetPortfoliosByUserIdQuery;
import com.acme.procedurescafelab.preparation.domain.services.PortfolioQueryService;
import com.acme.procedurescafelab.preparation.infrastructure.persistence.jpa.repositories.PortfolioRepository;
import com.acme.procedurescafelab.shared.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortfolioQueryServiceImpl implements PortfolioQueryService {
    private final PortfolioRepository portfolioRepository;

    public PortfolioQueryServiceImpl(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    @Override
    public List<Portfolio> handle(GetPortfoliosByUserIdQuery query) {
        return portfolioRepository.findByUserIdOrderByCreatedAtDesc(new UserId(query.userId()));
    }

    @Override
    public Optional<Portfolio> handle(GetPortfolioByIdForUserQuery query) {
        return portfolioRepository.findByIdAndUserId(query.portfolioId(), new UserId(query.userId()));
    }
}
