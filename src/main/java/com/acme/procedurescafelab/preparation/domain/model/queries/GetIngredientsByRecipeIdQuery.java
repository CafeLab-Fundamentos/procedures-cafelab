package com.acme.procedurescafelab.preparation.domain.model.queries;

public record GetIngredientsByRecipeIdQuery(Long recipeId) {
    public GetIngredientsByRecipeIdQuery {
        if (recipeId == null || recipeId <= 0) throw new IllegalArgumentException("RecipeId es requerido y debe ser positivo");
    }
}
