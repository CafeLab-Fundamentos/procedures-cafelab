package com.acme.procedurescafelab.preparation.interfaces.rest.resources;

public record UpdateIngredientResource(
    String name,
    Double amount,
    String unit
) {
    public UpdateIngredientResource {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name es requerido");
        if (amount == null || amount <= 0) throw new IllegalArgumentException("Amount es requerido y debe ser positivo");
        if (unit == null || unit.isBlank()) throw new IllegalArgumentException("Unit es requerido");
    }
}
