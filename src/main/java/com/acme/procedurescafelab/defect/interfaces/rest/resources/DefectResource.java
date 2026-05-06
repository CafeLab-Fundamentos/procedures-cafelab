package com.acme.procedurescafelab.defect.interfaces.rest.resources;

public record DefectResource(
        Long id,
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
}
