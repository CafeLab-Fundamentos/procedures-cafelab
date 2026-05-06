package com.acme.procedurescafelab.preparation.infrastructure.persistence.jpa.repositories;

import com.acme.procedurescafelab.preparation.domain.model.aggregates.Recipe;
import com.acme.procedurescafelab.shared.domain.model.valueobjects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByUserIdOrderByCreatedAtDesc(UserId userId);

    Optional<Recipe> findByIdAndUserId(Long id, UserId userId);

    List<Recipe> findByPortfolioId(Long portfolioId);
}
