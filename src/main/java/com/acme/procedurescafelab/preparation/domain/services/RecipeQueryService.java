package com.acme.procedurescafelab.preparation.domain.services;

import com.acme.procedurescafelab.preparation.domain.model.aggregates.Recipe;
import com.acme.procedurescafelab.preparation.domain.model.queries.GetRecipeByIdForUserQuery;
import com.acme.procedurescafelab.preparation.domain.model.queries.GetRecipesByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface RecipeQueryService {
    List<Recipe> handle(GetRecipesByUserIdQuery query);

    Optional<Recipe> handle(GetRecipeByIdForUserQuery query);
}
