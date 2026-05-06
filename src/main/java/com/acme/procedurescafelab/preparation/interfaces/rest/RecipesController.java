package com.acme.procedurescafelab.preparation.interfaces.rest;

import com.acme.procedurescafelab.preparation.domain.exceptions.RecipeNotFoundException;
import com.acme.procedurescafelab.preparation.interfaces.acl.PreparationContextFacade;
import com.acme.procedurescafelab.preparation.interfaces.rest.resources.CreateRecipeResource;
import com.acme.procedurescafelab.preparation.interfaces.rest.resources.UpdateRecipeResource;
import com.acme.procedurescafelab.preparation.interfaces.rest.transform.CreateRecipeCommandFromResourceAssembler;
import com.acme.procedurescafelab.preparation.interfaces.rest.transform.RecipeResourceFromEntityAssembler;
import com.acme.procedurescafelab.preparation.interfaces.rest.transform.UpdateRecipeCommandFromResourceAssembler;
import com.acme.procedurescafelab.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Recipes", description = "Recetas de preparación por perfil (barista)")
public class RecipesController {
    private final PreparationContextFacade preparationContextFacade;

    public RecipesController(PreparationContextFacade preparationContextFacade) {
        this.preparationContextFacade = preparationContextFacade;
    }

    private ResponseEntity<?> unauthorized(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResource(message));
    }

    @Operation(summary = "Crear receta (perfil desde JWT)")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRecipe(@RequestBody CreateRecipeResource resource) {
        try {
            var command = CreateRecipeCommandFromResourceAssembler.toCommand(resource);
            var created = preparationContextFacade.createRecipe(command);
            if (created.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new MessageResource("No se pudo crear la receta. Intente de nuevo."));
            }
            var ingredients = preparationContextFacade.getIngredientsByRecipeId(created.get().getId());
            var recipeResource =
                    RecipeResourceFromEntityAssembler.toResourceFromEntity(created.get(), ingredients);
            return new ResponseEntity<>(recipeResource, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new MessageResource(ex.getMessage()));
        }
    }

    @Operation(summary = "Listar recetas del perfil autenticado")
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getRecipesForCurrentProfile(@PathVariable Long userId) {
        var recipes = preparationContextFacade.getRecipesByUserId(userId);
        var recipeResources = recipes.stream()
                .map(recipe -> {
                    var ingredients =
                            preparationContextFacade.getIngredientsByRecipeId(recipe.getId());
                    return RecipeResourceFromEntityAssembler.toResourceFromEntity(recipe, ingredients);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(recipeResources);
    }

    @Operation(summary = "Obtener receta por id (solo si pertenece al perfil)")
    @GetMapping("/user/{userId}/{recipeId}")
    public ResponseEntity<?> getRecipeById(
            @PathVariable Long userId,
            @PathVariable Long recipeId
    ) {
        var recipe = preparationContextFacade.getRecipeByIdForUser(recipeId, userId);
        if (recipe.isEmpty()) {
            throw new RecipeNotFoundException(recipeId);
        }
        var ingredients = preparationContextFacade.getIngredientsByRecipeId(recipeId);
        var recipeResource = RecipeResourceFromEntityAssembler.toResourceFromEntity(recipe.get(), ingredients);
        return ResponseEntity.ok(recipeResource);
    }

    @Operation(summary = "Actualizar receta")
    @PutMapping(value = "user/{userId}/{recipeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRecipe(
            @PathVariable Long userId,
            @PathVariable Long recipeId,
            @RequestBody UpdateRecipeResource resource
    ) {
        try {
            var updateCommand =
                    UpdateRecipeCommandFromResourceAssembler.toCommandFromResource(
                            userId, recipeId, resource);
            var updated = preparationContextFacade.updateRecipe(updateCommand);
            if (updated.isEmpty()) {
                throw new RecipeNotFoundException(recipeId);
            }
            var ingredients = preparationContextFacade.getIngredientsByRecipeId(recipeId);
            var recipeResource =
                    RecipeResourceFromEntityAssembler.toResourceFromEntity(updated.get(), ingredients);
            return ResponseEntity.ok(recipeResource);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new MessageResource(ex.getMessage()));
        }
    }

    @Operation(summary = "Eliminar receta")
    @DeleteMapping("/user/{userId}/{recipeId}")
    public ResponseEntity<?> deleteRecipe(
            @PathVariable Long userId,
            @PathVariable Long recipeId
    ) {
        boolean deleted = preparationContextFacade.deleteRecipe(recipeId, userId);
        if (deleted) {
            return ResponseEntity.ok(new MessageResource("Receta eliminada exitosamente"));
        }
        throw new RecipeNotFoundException(recipeId);
    }
}
