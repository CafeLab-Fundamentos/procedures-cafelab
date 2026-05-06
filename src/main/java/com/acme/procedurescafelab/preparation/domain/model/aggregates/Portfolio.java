package com.acme.procedurescafelab.preparation.domain.model.aggregates;

import com.acme.procedurescafelab.preparation.domain.model.commands.CreatePortfolioCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.UpdatePortfolioCommand;
import com.acme.procedurescafelab.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.procedurescafelab.shared.domain.model.valueobjects.UserId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Portafolio; {@code userId} persiste en columna {@code user_id} (FK a profiles.id).
 */
@Getter
@Setter
@Entity
@Table(name = "portfolios")
public class Portfolio extends AuditableAbstractAggregateRoot<Portfolio> {

    @Embedded
    @Column(name = "user_id", nullable = false)
    private UserId userId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    public Portfolio() {}

    public Portfolio(Long userId, String name) {
        this.userId = new UserId(userId);
        this.name = name;
    }

    public Portfolio(CreatePortfolioCommand command) {
        this.userId = new UserId(command.userId());
        this.name = command.name();
    }

    public Portfolio update(UpdatePortfolioCommand command) {
        this.name = command.name();
        return this;
    }
}
