package com.acme.procedurescafelab.defect.domain.services;

import com.acme.procedurescafelab.defect.domain.model.aggregates.Defect;
import com.acme.procedurescafelab.defect.domain.model.commands.CreateDefectCommand;

import java.util.Optional;

public interface DefectCommandService {
    /**
     * Handle Create Defect Command
     *
     * @param command The {@link CreateDefectCommand} Command
     * @return A {@link Defect} instance if the command is valid, otherwise empty
     * @throws IllegalArgumentException if the defect is invalid or already exists (opcional)
     */
    Optional<Defect> handle(CreateDefectCommand command);
}
