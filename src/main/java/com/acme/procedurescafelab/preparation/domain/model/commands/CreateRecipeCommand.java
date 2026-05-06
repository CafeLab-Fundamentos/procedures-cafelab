package com.acme.procedurescafelab.preparation.domain.model.commands;

public record CreateRecipeCommand(
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
    String grindSize
) {
    public CreateRecipeCommand {
        if (userId == null || userId <= 0) throw new IllegalArgumentException("userId es requerido y debe ser positivo");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name es requerido");
        if (imageUrl == null || imageUrl.isBlank()) throw new IllegalArgumentException("ImageUrl es requerido");
        if (extractionMethod == null || extractionMethod.isBlank()) throw new IllegalArgumentException("ExtractionMethod es requerido");
        if (extractionCategory == null || extractionCategory.isBlank()) throw new IllegalArgumentException("ExtractionCategory es requerido");
        if (ratio == null || ratio.isBlank()) throw new IllegalArgumentException("Ratio es requerido");
        if (preparationTime == null || preparationTime < 0) throw new IllegalArgumentException("PreparationTime no puede ser negativo");
        if (steps == null || steps.isBlank()) throw new IllegalArgumentException("Steps es requerido");
        if (cupping == null) throw new IllegalArgumentException("Cupping no puede ser null");
        if (grindSize == null) throw new IllegalArgumentException("GrindSize no puede ser null");
    }
}
