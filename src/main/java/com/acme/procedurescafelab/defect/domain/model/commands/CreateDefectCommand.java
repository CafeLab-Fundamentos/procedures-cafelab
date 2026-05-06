package com.acme.procedurescafelab.defect.domain.model.commands;

public record CreateDefectCommand(
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
