package com.acme.procedurescafelab.preparation.application.internal.commandservices;

import com.acme.procedurescafelab.preparation.domain.model.aggregates.Portfolio;
import com.acme.procedurescafelab.preparation.domain.model.commands.CreatePortfolioCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.DeletePortfolioCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.UpdatePortfolioCommand;
import com.acme.procedurescafelab.preparation.domain.services.PortfolioCommandService;
import com.acme.procedurescafelab.preparation.infrastructure.persistence.jpa.repositories.PortfolioRepository;
import com.acme.procedurescafelab.shared.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PortfolioCommandServiceImpl implements PortfolioCommandService {
    private final PortfolioRepository portfolioRepository;

    public PortfolioCommandServiceImpl(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    @Override
    public Long handle(CreatePortfolioCommand command) {
        var portfolio = new Portfolio(command);
        portfolio = portfolioRepository.save(portfolio);
        return portfolio.getId();
    }

    @Override
    public Optional<Portfolio> handle(UpdatePortfolioCommand command) {
        try {
            var existing = portfolioRepository.findByIdAndUserId(command.portfolioId(), new UserId(command.userId()));
            if (existing.isEmpty()) {
                return Optional.empty();
            }
            var portfolio = existing.get();
            portfolio.update(command);
            return Optional.of(portfolioRepository.save(portfolio));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean handle(DeletePortfolioCommand command) {
        try {
            var existing = portfolioRepository.findByIdAndUserId(command.portfolioId(), new UserId(command.userId()));
            if (existing.isEmpty()) {
                return false;
            }
            portfolioRepository.deleteById(command.portfolioId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
