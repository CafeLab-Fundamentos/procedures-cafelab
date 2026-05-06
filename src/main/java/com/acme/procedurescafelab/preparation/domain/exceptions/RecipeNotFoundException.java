package com.acme.procedurescafelab.preparation.domain.exceptions;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(Long recipeId) {
        super("Receta no encontrada");
    }
}
