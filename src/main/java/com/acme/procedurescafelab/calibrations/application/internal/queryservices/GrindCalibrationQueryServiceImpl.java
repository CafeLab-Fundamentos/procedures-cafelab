package com.acme.procedurescafelab.calibrations.application.internal.queryservices;

import com.acme.procedurescafelab.calibrations.domain.model.aggregates.GrindCalibration;
import com.acme.procedurescafelab.calibrations.domain.model.queries.GetGrindCalibrationByIdForUserQuery;
import com.acme.procedurescafelab.calibrations.domain.model.queries.GetGrindCalibrationsByUserIdQuery;
import com.acme.procedurescafelab.calibrations.domain.services.GrindCalibrationQueryService;
import com.acme.procedurescafelab.calibrations.infrastructure.persistence.jpa.repositories.GrindCalibrationRepository;
import com.acme.procedurescafelab.shared.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrindCalibrationQueryServiceImpl implements GrindCalibrationQueryService {
    private final GrindCalibrationRepository repository;

    public GrindCalibrationQueryServiceImpl(GrindCalibrationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GrindCalibration> handle(GetGrindCalibrationsByUserIdQuery query) {
        return repository.findByUserIdOrderByCreatedAtDesc(new UserId(query.userId()));
    }

    @Override
    public Optional<GrindCalibration> handle(GetGrindCalibrationByIdForUserQuery query) {
        return repository.findByIdAndUserId(query.calibrationId(), new UserId(query.userId()));
    }
}
