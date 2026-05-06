package com.acme.procedurescafelab.preparation.domain.model.commands;

public record UpdatePortfolioCommand(Long userId, Long portfolioId, String name) {
    public UpdatePortfolioCommand {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId es requerido y debe ser positivo");
        }
        if (portfolioId == null || portfolioId <= 0) {
            throw new IllegalArgumentException("PortfolioId es requerido y debe ser positivo");
        }
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name es requerido");
        if (name.length() > 100) throw new IllegalArgumentException("Name no puede exceder 100 caracteres");
    }
}
