package com.acme.procedurescafelab.preparation.interfaces.rest.resources;

public record CreateRecipeResource(
        Long userId,
        String name,
        String imageUrl,
        String extractionMethod,
        String extractionCategory,
        String ratio,
        Long cuppingSessionId,
        Long portfolioId,
        Integer preparationTime,
        String steps,
        String tips,
        String cupping,
        String grindSize) {
    public CreateRecipeResource {
        if (userId == null || userId <= 0) throw new IllegalArgumentException("userId es requerido y debe ser positivo");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name es requerido");
        if (imageUrl == null || imageUrl.isBlank()) throw new IllegalArgumentException("ImageUrl es requerido");
        if (extractionMethod == null || extractionMethod.isBlank()) {
            throw new IllegalArgumentException("ExtractionMethod es requerido");
        }
        if (extractionCategory == null || extractionCategory.isBlank()) {
            throw new IllegalArgumentException("ExtractionCategory es requerido");
        }
        if (ratio == null || ratio.isBlank()) throw new IllegalArgumentException("Ratio es requerido");
        if (preparationTime == null || preparationTime < 0) {
            throw new IllegalArgumentException("PreparationTime es requerido y no puede ser negativo");
        }
        if (steps == null || steps.isBlank()) throw new IllegalArgumentException("Steps es requerido");
        tips = tips == null ? "" : tips;
        cupping = cupping == null ? "" : cupping;
        grindSize = (grindSize == null || grindSize.isBlank()) ? "-" : grindSize;
    }
}
