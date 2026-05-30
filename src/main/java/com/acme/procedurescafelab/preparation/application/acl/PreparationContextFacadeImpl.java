package com.acme.procedurescafelab.preparation.application.acl;

import com.acme.procedurescafelab.preparation.domain.model.aggregates.Ingredient;
import com.acme.procedurescafelab.preparation.domain.model.aggregates.Portfolio;
import com.acme.procedurescafelab.preparation.domain.model.aggregates.Recipe;
import com.acme.procedurescafelab.preparation.domain.model.commands.CreateIngredientCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.CreatePortfolioCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.CreateRecipeCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.DeleteIngredientCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.DeletePortfolioCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.DeleteRecipeCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.UpdateIngredientCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.UpdatePortfolioCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.UpdateRecipeCommand;
import com.acme.procedurescafelab.preparation.domain.model.queries.GetIngredientsByRecipeIdQuery;
import com.acme.procedurescafelab.preparation.domain.model.queries.GetPortfolioByIdForUserQuery;
import com.acme.procedurescafelab.preparation.domain.model.queries.GetPortfoliosByUserIdQuery;
import com.acme.procedurescafelab.preparation.domain.model.queries.GetRecipeByIdForUserQuery;
import com.acme.procedurescafelab.preparation.domain.model.queries.GetRecipesByUserIdQuery;
import com.acme.procedurescafelab.preparation.domain.services.IngredientCommandService;
import com.acme.procedurescafelab.preparation.domain.services.IngredientQueryService;
import com.acme.procedurescafelab.preparation.domain.services.PortfolioCommandService;
import com.acme.procedurescafelab.preparation.domain.services.PortfolioQueryService;
import com.acme.procedurescafelab.preparation.domain.services.RecipeCommandService;
import com.acme.procedurescafelab.preparation.domain.services.RecipeQueryService;
import com.acme.procedurescafelab.preparation.interfaces.acl.PreparationContextFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreparationContextFacadeImpl implements PreparationContextFacade {
    private final RecipeCommandService recipeCommandService;
    private final RecipeQueryService recipeQueryService;
    private final IngredientCommandService ingredientCommandService;
    private final IngredientQueryService ingredientQueryService;
    private final PortfolioCommandService portfolioCommandService;
    private final PortfolioQueryService portfolioQueryService;

    public PreparationContextFacadeImpl(
            RecipeCommandService recipeCommandService,
            RecipeQueryService recipeQueryService,
            IngredientCommandService ingredientCommandService,
            IngredientQueryService ingredientQueryService,
            PortfolioCommandService portfolioCommandService,
            PortfolioQueryService portfolioQueryService) {
        this.recipeCommandService = recipeCommandService;
        this.recipeQueryService = recipeQueryService;
        this.ingredientCommandService = ingredientCommandService;
        this.ingredientQueryService = ingredientQueryService;
        this.portfolioCommandService = portfolioCommandService;
        this.portfolioQueryService = portfolioQueryService;
    }

    @Override
    public Optional<Recipe> createRecipe(CreateRecipeCommand command) {
        return recipeCommandService.handle(command);
    }

    @Override
    public Optional<Recipe> updateRecipe(UpdateRecipeCommand command) {
        return recipeCommandService.handle(command);
    }

    @Override
    public boolean deleteRecipe(Long recipeId, Long userId) {
        return recipeCommandService.handle(new DeleteRecipeCommand(recipeId, userId));
    }

    @Override
    public List<Recipe> getRecipesByUserId(Long userId) {
        return recipeQueryService.handle(new GetRecipesByUserIdQuery(userId));
    }

    @Override
    public Optional<Recipe> getRecipeByIdForUser(Long recipeId, Long userId) {
        return recipeQueryService.handle(new GetRecipeByIdForUserQuery(recipeId, userId));
    }

    @Override
    public Long createIngredient(Long recipeId, String name, Double amount, String unit) {
        var createIngredientCommand = new CreateIngredientCommand(recipeId, name, amount, unit);
        var ingredient = ingredientCommandService.handle(createIngredientCommand);
        return ingredient.map(Ingredient::getId).orElse(0L);
    }

    @Override
    public Long updateIngredient(Long ingredientId, String name, Double amount, String unit) {
        var updateIngredientCommand = new UpdateIngredientCommand(ingredientId, name, amount, unit);
        var ingredient = ingredientCommandService.handle(updateIngredientCommand);
        return ingredient.map(Ingredient::getId).orElse(0L);
    }

    @Override
    public boolean deleteIngredient(Long recipeId, Long ingredientId) {
        var deleteIngredientCommand = new DeleteIngredientCommand(recipeId, ingredientId);
        return ingredientCommandService.handle(deleteIngredientCommand);
    }

    @Override
    public List<Ingredient> getIngredientsByRecipeId(Long recipeId) {
        return ingredientQueryService.handle(new GetIngredientsByRecipeIdQuery(recipeId));
    }

    @Override
    public Long createPortfolio(Long userId, String name) {
        return portfolioCommandService.handle(new CreatePortfolioCommand(userId, name));
    }

    @Override
    public Optional<Portfolio> updatePortfolio(UpdatePortfolioCommand command) {
        return portfolioCommandService.handle(command);
    }

    @Override
    public boolean deletePortfolio(Long portfolioId, Long userId) {
        return portfolioCommandService.handle(new DeletePortfolioCommand(portfolioId, userId));
    }

    @Override
    public List<Portfolio> getPortfoliosByUserId(Long userId) {
        return portfolioQueryService.handle(new GetPortfoliosByUserIdQuery(userId));
    }

    @Override
    public Optional<Portfolio> getPortfolioByIdForUser(Long portfolioId, Long userId) {
        return portfolioQueryService.handle(new GetPortfolioByIdForUserQuery(portfolioId, userId));
    }
}
