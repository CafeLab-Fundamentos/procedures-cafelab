package com.acme.procedurescafelab.defect.application.internal.queryservices;

import com.acme.procedurescafelab.defect.domain.model.aggregates.Defect;
import com.acme.procedurescafelab.defect.domain.model.queries.GetDefectByIdForUserQuery;
import com.acme.procedurescafelab.defect.domain.model.queries.GetDefectsByUserIdQuery;
import com.acme.procedurescafelab.defect.domain.services.DefectQueryService;
import com.acme.procedurescafelab.defect.infrastructure.persistence.jpa.repositories.DefectRepository;
import com.acme.procedurescafelab.shared.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefectQueryServiceImpl implements DefectQueryService {
    private final DefectRepository defectRepository;

    public DefectQueryServiceImpl(DefectRepository defectRepository) {
        this.defectRepository = defectRepository;
    }

    @Override
    public List<Defect> handle(GetDefectsByUserIdQuery query) {
        return defectRepository.findByUserIdOrderByCreatedAtDesc(new UserId(query.userId()));
    }

    @Override
    public Optional<Defect> handle(GetDefectByIdForUserQuery query) {
        return defectRepository.findByIdAndUserId(query.defectId(), new UserId(query.userId()));
    }
}
