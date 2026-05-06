package com.acme.procedurescafelab.preparation.interfaces.rest.resources;

public record CreateIngredientResource(
    Long recipeId,
    String name,
    Double amount,
    String unit
) {
    public CreateIngredientResource {
        if (recipeId == null || recipeId <= 0) throw new IllegalArgumentException("RecipeId es requerido y debe ser positivo");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name es requerido");
        if (amount == null) throw new IllegalArgumentException("Amount es requerido");
        if (unit == null || unit.isBlank()) throw new IllegalArgumentException("Unit es requerido");
    }
}
