package com.acme.procedurescafelab.preparation.domain.model.queries;

public record GetRecipesByUserIdQuery(Long userId) {
    public GetRecipesByUserIdQuery {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId es requerido");
        }
    }
}
