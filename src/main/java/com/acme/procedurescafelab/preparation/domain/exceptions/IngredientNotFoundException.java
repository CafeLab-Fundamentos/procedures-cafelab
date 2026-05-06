package com.acme.procedurescafelab.preparation.domain.exceptions;

public class IngredientNotFoundException extends RuntimeException {
    public IngredientNotFoundException(Long ingredientId) {
        super("Ingrediente no encontrado");
    }
}
