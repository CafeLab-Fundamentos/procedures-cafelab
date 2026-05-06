package com.acme.procedurescafelab.defect.interfaces.rest.resources;

public record CreateDefectResource(
        Long userId,
        String coffeeDisplayName,
        String coffeeRegion,
        String coffeeVariety,
        Double coffeeTotalWeight,
        String name,
        String defectType,
        Double defectWeight,
        Double percentage,
        String probableCause,
        String suggestedSolution
) {
    public CreateDefectResource {
        if (userId == null) {
            throw new IllegalArgumentException("El ID del usuario es obligatorio.");
        }
        if (coffeeDisplayName == null || coffeeDisplayName.isBlank()) {
            throw new IllegalArgumentException("El nombre del café es obligatorio.");
        }
        if (coffeeTotalWeight != null && coffeeTotalWeight < 0) {
            throw new IllegalArgumentException("El peso total del café no puede ser negativo.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre del defecto es obligatorio.");
        }
        if (defectType == null || defectType.isBlank()) {
            throw new IllegalArgumentException("El tipo de defecto es obligatorio.");
        }
        if (defectWeight == null || defectWeight <= 0) {
            throw new IllegalArgumentException("El peso del defecto debe ser mayor que cero.");
        }
        if (percentage == null || percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100.");
        }
        if (probableCause == null || probableCause.isBlank()) {
            throw new IllegalArgumentException("La causa probable es obligatoria.");
        }
        if (suggestedSolution == null || suggestedSolution.isBlank()) {
            throw new IllegalArgumentException("La solución sugerida es obligatoria.");
        }
    }
}
