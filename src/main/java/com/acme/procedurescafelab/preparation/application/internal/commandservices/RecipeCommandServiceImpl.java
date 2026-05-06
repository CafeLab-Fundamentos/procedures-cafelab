package com.acme.procedurescafelab.preparation.application.internal.commandservices;

import com.acme.procedurescafelab.cuppingSession.domain.model.queries.GetCuppingSessionByIdForUserQuery;
import com.acme.procedurescafelab.cuppingSession.domain.services.CuppingSessionQueryService;
import com.acme.procedurescafelab.preparation.domain.model.aggregates.Recipe;
import com.acme.procedurescafelab.preparation.domain.model.commands.CreateRecipeCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.DeleteRecipeCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.UpdateRecipeCommand;
import com.acme.procedurescafelab.preparation.domain.services.RecipeCommandService;
import com.acme.procedurescafelab.preparation.infrastructure.persistence.jpa.repositories.IngredientRepository;
import com.acme.procedurescafelab.preparation.infrastructure.persistence.jpa.repositories.PortfolioRepository;
import com.acme.procedurescafelab.preparation.infrastructure.persistence.jpa.repositories.RecipeRepository;
import com.acme.procedurescafelab.shared.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipeCommandServiceImpl implements RecipeCommandService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final PortfolioRepository portfolioRepository;
    private final CuppingSessionQueryService cuppingSessionQueryService;

    public RecipeCommandServiceImpl(
            RecipeRepository recipeRepository,
            IngredientRepository ingredientRepository,
            PortfolioRepository portfolioRepository,
            CuppingSessionQueryService cuppingSessionQueryService) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.portfolioRepository = portfolioRepository;
        this.cuppingSessionQueryService = cuppingSessionQueryService;
    }

    @Override
    public Optional<Recipe> handle(CreateRecipeCommand command) {
        assertCuppingSessionBelongsToUser(command.userId(), command.cuppingSessionId());
        assertPortfolioBelongsToUser(command.userId(), command.portfolioId());
        try {
            var recipe = new Recipe(command);
            return Optional.of(recipeRepository.save(recipe));
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "No se pudo guardar la receta. Revise los datos (nombre, pasos, tiempos) e intente de nuevo.",
                    e);
        }
    }

    @Override
    public Optional<Recipe> handle(UpdateRecipeCommand command) {
        var existing = recipeRepository.findByIdAndUserId(command.recipeId(), new UserId(command.userId()));
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        assertCuppingSessionBelongsToUser(command.userId(), command.cuppingSessionId());
        assertPortfolioBelongsToUser(command.userId(), command.portfolioId());
        try {
            var recipe = existing.get();
            recipe.update(command);
            return Optional.of(recipeRepository.save(recipe));
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "No se pudo actualizar la receta. Revise los datos e intente de nuevo.", e);
        }
    }

    @Override
    public boolean handle(DeleteRecipeCommand command) {
        try {
            var existing = recipeRepository.findByIdAndUserId(command.recipeId(), new UserId(command.userId()));
            if (existing.isEmpty()) {
                return false;
            }
            ingredientRepository.deleteAll(ingredientRepository.findByRecipeId(command.recipeId()));
            recipeRepository.deleteById(command.recipeId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void assertCuppingSessionBelongsToUser(Long userId, Long cuppingSessionId) {
        if (cuppingSessionId == null) {
            return;
        }
        if (cuppingSessionQueryService
                .handle(new GetCuppingSessionByIdForUserQuery(cuppingSessionId, userId))
                .isEmpty()) {
            throw new IllegalArgumentException(
                    "La sesión de cata indicada no existe o no pertenece a su perfil. Elija otra o deje \"Sin sesión de cata\".");
        }
    }

    private void assertPortfolioBelongsToUser(Long userId, Long portfolioId) {
        if (portfolioId == null) {
            return;
        }
        if (portfolioRepository.findByIdAndUserId(portfolioId, new UserId(userId)).isEmpty()) {
            throw new IllegalArgumentException(
                    "El portafolio indicado no existe o no pertenece a su perfil. Elija otro o deje \"Sin portafolio\".");
        }
    }
}
