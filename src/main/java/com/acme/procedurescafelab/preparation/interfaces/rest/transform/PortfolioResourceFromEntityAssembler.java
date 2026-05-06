package com.acme.procedurescafelab.preparation.interfaces.rest.transform;

import com.acme.procedurescafelab.preparation.domain.model.aggregates.Portfolio;
import com.acme.procedurescafelab.preparation.interfaces.rest.resources.PortfolioResource;

public class PortfolioResourceFromEntityAssembler {
    public static PortfolioResource toResourceFromEntity(Portfolio entity) {
        return new PortfolioResource(
                entity.getId(),
                entity.getUserId().userId(),
                entity.getName(),
                entity.getCreatedAt().toString());
    }
}
