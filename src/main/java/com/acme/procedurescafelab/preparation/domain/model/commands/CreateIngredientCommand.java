package com.acme.procedurescafelab.preparation.domain.model.commands;

public record CreateIngredientCommand(
    Long recipeId,
    String name,
    Double amount,
    String unit
) {
    public CreateIngredientCommand {
        if (recipeId == null || recipeId <= 0) throw new IllegalArgumentException("RecipeId es requerido y debe ser positivo");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name es requerido");
        if (amount == null) throw new IllegalArgumentException("Amount es requerido");
        if (unit == null || unit.isBlank()) throw new IllegalArgumentException("Unit es requerido");
    }
}
