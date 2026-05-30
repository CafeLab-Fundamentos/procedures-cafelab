package com.acme.procedurescafelab.preparation.interfaces.rest;

import com.acme.procedurescafelab.preparation.domain.exceptions.IngredientNotFoundException;
import com.acme.procedurescafelab.preparation.interfaces.acl.PreparationContextFacade;
import com.acme.procedurescafelab.preparation.interfaces.rest.resources.CreateIngredientResource;
import com.acme.procedurescafelab.preparation.interfaces.rest.resources.IngredientResource;
import com.acme.procedurescafelab.preparation.interfaces.rest.resources.UpdateIngredientResource;
import com.acme.procedurescafelab.preparation.interfaces.rest.transform.UpdateIngredientCommandFromResourceAssembler;
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
@RequestMapping(value = "/api/v1/recipes/{recipeId}/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Recipes", description = "Ingredientes de recetas (ámbito por perfil)")
public class IngredientsController {
    private final PreparationContextFacade preparationContextFacade;

    public IngredientsController(PreparationContextFacade preparationContextFacade) {
        this.preparationContextFacade = preparationContextFacade;
    }

    private boolean recipeOwned(Long recipeId, Long userId) {
        return preparationContextFacade.getRecipeByIdForUser(recipeId, userId).isPresent();
    }

    private ResponseEntity<?> forbidden(String message) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResource(message));
    }

    @Operation(summary = "Añadir ingrediente a una receta del perfil")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addIngredientToRecipe(
            @PathVariable Long recipeId, @RequestBody CreateIngredientResource resource) {
        if (!recipeId.equals(resource.recipeId())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResource("El ID de la receta no coincide con el de la ruta"));
        }

        var ingredientId = preparationContextFacade.createIngredient(
                resource.recipeId(), resource.name(), resource.amount(), resource.unit());

        if (ingredientId == 0L) {
            return ResponseEntity.badRequest().body(new MessageResource("No se pudo crear el ingrediente"));
        }

        var ingredients = preparationContextFacade.getIngredientsByRecipeId(recipeId);
        var ingredient = ingredients.stream().filter(i -> i.getId().equals(ingredientId)).findFirst();

        if (ingredient.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new MessageResource("No se pudo obtener el ingrediente creado"));
        }

        var ingredientResource = new IngredientResource(
                ingredient.get().getId(),
                ingredient.get().getRecipeId(),
                ingredient.get().getName(),
                ingredient.get().getAmount(),
                ingredient.get().getUnit());

        return new ResponseEntity<>(ingredientResource, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar ingredientes de la receta")
    @GetMapping
    public ResponseEntity<?> getIngredientsByRecipeId(@PathVariable Long recipeId) {
        var ingredients = preparationContextFacade.getIngredientsByRecipeId(recipeId);
        var ingredientResources = ingredients.stream()
                .map(
                        ingredient ->
                                new IngredientResource(
                                        ingredient.getId(),
                                        ingredient.getRecipeId(),
                                        ingredient.getName(),
                                        ingredient.getAmount(),
                                        ingredient.getUnit()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ingredientResources);
    }

    @Operation(summary = "Actualizar ingrediente")
    @PutMapping("/{ingredientId}")
    public ResponseEntity<?> updateIngredient(
            @PathVariable Long recipeId,
            @PathVariable Long ingredientId,
            @RequestBody UpdateIngredientResource resource) {

        var updateIngredientCommand =
                UpdateIngredientCommandFromResourceAssembler.toCommandFromResource(ingredientId, resource);
        var updatedIngredientId = preparationContextFacade.updateIngredient(
                updateIngredientCommand.ingredientId(),
                updateIngredientCommand.name(),
                updateIngredientCommand.amount(),
                updateIngredientCommand.unit());

        if (updatedIngredientId == 0L) {
            throw new IngredientNotFoundException(ingredientId);
        }

        var ingredients = preparationContextFacade.getIngredientsByRecipeId(recipeId);
        var ingredient = ingredients.stream()
                .filter(i -> i.getId().equals(updatedIngredientId))
                .findFirst();

        if (ingredient.isEmpty()) {
            throw new IngredientNotFoundException(ingredientId);
        }

        var ingredientResource = new IngredientResource(
                ingredient.get().getId(),
                ingredient.get().getRecipeId(),
                ingredient.get().getName(),
                ingredient.get().getAmount(),
                ingredient.get().getUnit());

        return ResponseEntity.ok(ingredientResource);
    }

    @Operation(summary = "Eliminar ingrediente")
    @DeleteMapping("/{ingredientId}")
    public ResponseEntity<?> deleteIngredient(
            @PathVariable Long recipeId, @PathVariable Long ingredientId) {
        var ingredients = preparationContextFacade.getIngredientsByRecipeId(recipeId);
        boolean belongs = ingredients.stream()
                .anyMatch(i -> i.getId() != null && i.getId().equals(ingredientId));
        if (!belongs) {
            return forbidden("Ingrediente no pertenece a esta receta");
        }
        var deleted = preparationContextFacade.deleteIngredient(recipeId, ingredientId);
        if (deleted) {
            return ResponseEntity.ok(new MessageResource("Ingrediente eliminado exitosamente"));
        }
        throw new IngredientNotFoundException(ingredientId);
    }
}
