package com.acme.procedurescafelab.preparation.interfaces.acl;

import com.acme.procedurescafelab.preparation.domain.model.aggregates.Ingredient;
import com.acme.procedurescafelab.preparation.domain.model.aggregates.Portfolio;
import com.acme.procedurescafelab.preparation.domain.model.aggregates.Recipe;
import com.acme.procedurescafelab.preparation.domain.model.commands.CreateRecipeCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.UpdatePortfolioCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.UpdateRecipeCommand;

import java.util.List;
import java.util.Optional;

public interface PreparationContextFacade {

    Optional<Recipe> createRecipe(CreateRecipeCommand command);

    Optional<Recipe> updateRecipe(UpdateRecipeCommand command);

    boolean deleteRecipe(Long recipeId, Long userId);

    List<Recipe> getRecipesByUserId(Long userId);

    Optional<Recipe> getRecipeByIdForUser(Long recipeId, Long userId);

    Long createIngredient(Long recipeId, String name, Double amount, String unit);

    Long updateIngredient(Long ingredientId, String name, Double amount, String unit);

    boolean deleteIngredient(Long recipeId, Long ingredientId);

    List<Ingredient> getIngredientsByRecipeId(Long recipeId);

    Long createPortfolio(Long userId, String name);

    Optional<Portfolio> updatePortfolio(UpdatePortfolioCommand command);

    boolean deletePortfolio(Long portfolioId, Long userId);

    List<Portfolio> getPortfoliosByUserId(Long userId);

    Optional<Portfolio> getPortfolioByIdForUser(Long portfolioId, Long userId);
}
