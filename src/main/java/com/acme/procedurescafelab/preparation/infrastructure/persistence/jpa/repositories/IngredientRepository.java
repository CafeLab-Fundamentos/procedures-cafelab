package com.acme.procedurescafelab.preparation.infrastructure.persistence.jpa.repositories;

import com.acme.procedurescafelab.preparation.domain.model.aggregates.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByRecipeId(Long recipeId);
}
