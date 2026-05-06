package com.acme.procedurescafelab.preparation.domain.model.queries;

public record GetPortfolioByIdForUserQuery(Long portfolioId, Long userId) {
    public GetPortfolioByIdForUserQuery {
        if (portfolioId == null || portfolioId <= 0) {
            throw new IllegalArgumentException("portfolioId es requerido");
        }
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId es requerido");
        }
    }
}
