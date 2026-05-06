package com.acme.procedurescafelab.defect.application.internal.commandservices;

import com.acme.procedurescafelab.defect.domain.model.aggregates.Defect;
import com.acme.procedurescafelab.defect.domain.model.commands.CreateDefectCommand;
import com.acme.procedurescafelab.defect.domain.services.DefectCommandService;
import com.acme.procedurescafelab.defect.infrastructure.persistence.jpa.repositories.DefectRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefectCommandServiceImpl implements DefectCommandService {
    private final DefectRepository defectRepository;

    /**
     * Constructor
     *
     * @param defectRepository The {@link DefectRepository} instance
     */
    public DefectCommandServiceImpl(DefectRepository defectRepository) {
        this.defectRepository = defectRepository;
    }

    @Override
    public Optional<Defect> handle(CreateDefectCommand command) {
        var defect = new Defect(command);
        defectRepository.save(defect);
        return Optional.of(defect);
    }
}
