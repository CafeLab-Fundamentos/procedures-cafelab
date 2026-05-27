package com.acme.procedurescafelab.preparation.domain.model.aggregates;

import com.acme.procedurescafelab.preparation.domain.model.commands.CreateIngredientCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.UpdateIngredientCommand;
import com.acme.procedurescafelab.preparation.domain.model.valueobjects.IngredientName;
import com.acme.procedurescafelab.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Ingredient extends AuditableAbstractAggregateRoot<Ingredient> {

    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name", length = 100))
    private IngredientName name;

    @Column(nullable = false)
    private Double amount;

    @Column(length = 10, nullable = false)
    private String unit;

    
    public Ingredient() {}

    
    public Ingredient(Long recipeId, String name, Double amount, String unit) {
        this.recipeId = recipeId;
        this.name = new IngredientName(name);
        this.amount = amount;
        this.unit = unit;
    }

    
    public Ingredient(CreateIngredientCommand command) {
        this.recipeId = command.recipeId();
        this.name = new IngredientName(command.name());
        this.amount = command.amount();
        this.unit = command.unit();
    }

    
    public Ingredient update(UpdateIngredientCommand command) {
        this.name = new IngredientName(command.name());
        this.amount = command.amount();
        this.unit = command.unit();
        return this;
    }

    public String getName() { return name.value(); }
}

// deploy