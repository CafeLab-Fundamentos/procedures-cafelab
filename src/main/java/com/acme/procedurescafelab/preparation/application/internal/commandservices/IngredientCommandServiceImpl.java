package com.acme.procedurescafelab.preparation.application.internal.commandservices;

import com.acme.procedurescafelab.preparation.domain.model.aggregates.Ingredient;
import com.acme.procedurescafelab.preparation.domain.model.commands.CreateIngredientCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.UpdateIngredientCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.DeleteIngredientCommand;
import com.acme.procedurescafelab.preparation.domain.services.IngredientCommandService;
import com.acme.procedurescafelab.preparation.infrastructure.persistence.jpa.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientCommandServiceImpl implements IngredientCommandService {
    private final IngredientRepository ingredientRepository;

    public IngredientCommandServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Optional<Ingredient> handle(CreateIngredientCommand command) {
        try {
            var ingredient = new Ingredient(command);
            return Optional.of(ingredientRepository.save(ingredient));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Ingredient> handle(UpdateIngredientCommand command) {
        try {
            var existingIngredient = ingredientRepository.findById(command.ingredientId());
            if (existingIngredient.isPresent()) {
                var ingredient = existingIngredient.get();
                ingredient.update(command);
                return Optional.of(ingredientRepository.save(ingredient));
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean handle(DeleteIngredientCommand command) {
        try {
            if (ingredientRepository.existsById(command.ingredientId())) {
                ingredientRepository.deleteById(command.ingredientId());
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
