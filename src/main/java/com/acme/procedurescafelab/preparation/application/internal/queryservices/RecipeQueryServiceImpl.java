package com.acme.procedurescafelab.preparation.application.internal.queryservices;

import com.acme.procedurescafelab.preparation.domain.model.aggregates.Recipe;
import com.acme.procedurescafelab.preparation.domain.model.queries.GetRecipeByIdForUserQuery;
import com.acme.procedurescafelab.preparation.domain.model.queries.GetRecipesByUserIdQuery;
import com.acme.procedurescafelab.preparation.domain.services.RecipeQueryService;
import com.acme.procedurescafelab.preparation.infrastructure.persistence.jpa.repositories.RecipeRepository;
import com.acme.procedurescafelab.shared.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeQueryServiceImpl implements RecipeQueryService {
    private final RecipeRepository recipeRepository;

    public RecipeQueryServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> handle(GetRecipesByUserIdQuery query) {
        return recipeRepository.findByUserIdOrderByCreatedAtDesc(new UserId(query.userId()));
    }

    @Override
    public Optional<Recipe> handle(GetRecipeByIdForUserQuery query) {
        return recipeRepository.findByIdAndUserId(query.recipeId(), new UserId(query.userId()));
    }
}
