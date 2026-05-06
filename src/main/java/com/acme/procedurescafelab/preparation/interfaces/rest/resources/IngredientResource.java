package com.acme.procedurescafelab.preparation.interfaces.rest.resources;

public record IngredientResource(
    Long id,
    Long recipeId,
    String name,
    Double amount,
    String unit
) {}
