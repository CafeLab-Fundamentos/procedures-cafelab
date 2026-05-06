package com.acme.procedurescafelab.preparation.domain.model.commands;

public record DeletePortfolioCommand(Long portfolioId, Long userId) {
    public DeletePortfolioCommand {
        if (portfolioId == null || portfolioId <= 0) {
            throw new IllegalArgumentException("PortfolioId es requerido y debe ser positivo");
        }
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId es requerido y debe ser positivo");
        }
    }
}
