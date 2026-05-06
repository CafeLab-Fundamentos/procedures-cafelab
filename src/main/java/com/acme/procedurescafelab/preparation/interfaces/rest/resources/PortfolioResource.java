package com.acme.procedurescafelab.preparation.interfaces.rest.resources;

/** Representación de portafolio; {@code userId} corresponde a la columna {@code user_id} en BD. */
public record PortfolioResource(Long id, Long userId, String name, String createdAt) {}
