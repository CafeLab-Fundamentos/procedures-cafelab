package com.acme.procedurescafelab.preparation.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record IngredientName(String value) {
    public IngredientName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El nombre del ingrediente no puede ser nulo o vacío");
        }
        if (value.length() > 100) {
            throw new IllegalArgumentException("El nombre del ingrediente no puede tener más de 100 caracteres");
        }
    }

    public IngredientName() {
        this(null);
    }
}
