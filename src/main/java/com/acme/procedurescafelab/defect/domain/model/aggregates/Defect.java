package com.acme.procedurescafelab.defect.domain.model.aggregates;

import com.acme.procedurescafelab.defect.domain.model.commands.CreateDefectCommand;
import com.acme.procedurescafelab.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.procedurescafelab.shared.domain.model.valueobjects.UserId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Defect extends AuditableAbstractAggregateRoot<Defect> {

    @Embedded
    @Column(name = "user_id")
    private UserId userId;

    @Column(name = "coffee_display_name")
    private String coffeeDisplayName;

    @Column(name = "coffee_region")
    private String coffeeRegion;

    @Column(name = "coffee_variety")
    private String coffeeVariety;

    @Column(name = "coffee_total_weight")
    private Double coffeeTotalWeight;

    private String name;
    private String defectType;

    @Column(nullable = false)
    private Double defectWeight;

    @Column(nullable = false)
    private Double percentage;

    private String probableCause;
    private String suggestedSolution;

    public Defect() {}

    public Defect(CreateDefectCommand command) {
        this.userId = new UserId(command.userId());
        this.coffeeDisplayName = command.coffeeDisplayName().trim();
        this.coffeeRegion = command.coffeeRegion();
        this.coffeeVariety = command.coffeeVariety();
        this.coffeeTotalWeight = command.coffeeTotalWeight();
        this.name = command.name();
        this.defectType = command.defectType();
        this.defectWeight = command.defectWeight();
        this.percentage = command.percentage();
        this.probableCause = command.probableCause();
        this.suggestedSolution = command.suggestedSolution();
    }
}
