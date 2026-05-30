package com.acme.procedurescafelab.preparation.domain.model.commands;

public record DeleteIngredientCommand(
    Long recipeId,
    Long ingredientId
) {
    public DeleteIngredientCommand {
        if (recipeId == null || recipeId <= 0) {
            throw new IllegalArgumentException("RecipeId es requerido y debe ser positivo");
        }
        if (ingredientId == null || ingredientId <= 0) {
            throw new IllegalArgumentException("IngredientId es requerido y debe ser positivo");
        }
    }
}
