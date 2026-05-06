package com.acme.procedurescafelab.preparation.interfaces.rest.resources;

import java.util.List;

public record RecipeResource(
    Long id,
    Long userId,
    String name,
    String imageUrl,
    String extractionMethod,
    String extractionCategory,
    String ratio,
    Long cuppingSessionId,
    Long portfolioId,
    Integer preparationTime,
    String steps,
    String tips,
    String cupping,
    String grindSize,
    String createdAt,
    List<IngredientResource> ingredients
) {}
