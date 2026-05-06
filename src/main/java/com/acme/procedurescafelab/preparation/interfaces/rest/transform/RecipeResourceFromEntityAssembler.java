package com.acme.procedurescafelab.preparation.interfaces.rest.transform;

import com.acme.procedurescafelab.preparation.domain.model.aggregates.Recipe;
import com.acme.procedurescafelab.preparation.domain.model.aggregates.Ingredient;
import com.acme.procedurescafelab.preparation.interfaces.rest.resources.RecipeResource;
import com.acme.procedurescafelab.preparation.interfaces.rest.resources.IngredientResource;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeResourceFromEntityAssembler {
    public static RecipeResource toResourceFromEntity(Recipe entity, List<Ingredient> ingredients) {
        List<IngredientResource> ingredientResources = ingredients.stream()
            .map(ingredient -> new IngredientResource(
                ingredient.getId(),
                ingredient.getRecipeId(),
                ingredient.getName(),
                ingredient.getAmount(),
                ingredient.getUnit()
            ))
            .collect(Collectors.toList());

        return new RecipeResource(
            entity.getId(),
            entity.getUserId().userId(),
            entity.getName(),
            entity.getImageUrl(),
            entity.getExtractionMethod().getValue(),
            entity.getExtractionCategory() != null ? entity.getExtractionCategory().getValue() : "coffee",
            entity.getRatio(),
            entity.getCuppingSessionId(),
            entity.getPortfolioId(),
            entity.getPreparationTime(),
            entity.getSteps(),
            entity.getTips(),
            entity.getCupping(),
            entity.getGrindSize(),
            entity.getCreatedAt().toString(),
            ingredientResources
        );
    }
}
