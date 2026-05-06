package com.acme.procedurescafelab.preparation.domain.services;

import com.acme.procedurescafelab.preparation.domain.model.aggregates.Ingredient;
import com.acme.procedurescafelab.preparation.domain.model.queries.GetIngredientsByRecipeIdQuery;

import java.util.List;

public interface IngredientQueryService {
    List<Ingredient> handle(GetIngredientsByRecipeIdQuery query);
}
