package com.acme.procedurescafelab.preparation.domain.model.queries;

public record GetRecipeByIdForUserQuery(Long recipeId, Long userId) {
    public GetRecipeByIdForUserQuery {
        if (recipeId == null || recipeId <= 0) {
            throw new IllegalArgumentException("recipeId es requerido");
        }
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId es requerido");
        }
    }
}
