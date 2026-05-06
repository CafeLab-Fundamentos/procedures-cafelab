package com.acme.procedurescafelab.preparation.interfaces.rest.resources;

public record UpdatePortfolioResource(
    String name
) {
    public UpdatePortfolioResource {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name es requerido");
        if (name.length() > 100) throw new IllegalArgumentException("Name no puede exceder 100 caracteres");
    }
}
