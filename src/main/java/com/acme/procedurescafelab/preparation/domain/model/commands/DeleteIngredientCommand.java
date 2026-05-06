package com.acme.procedurescafelab.preparation.domain.model.commands;

public record DeleteIngredientCommand(
    Long ingredientId
) {
    public DeleteIngredientCommand {
        if (ingredientId == null || ingredientId <= 0) throw new IllegalArgumentException("IngredientId es requerido y debe ser positivo");
    }
}
