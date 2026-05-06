package com.acme.procedurescafelab.calibrations.infrastructure.persistence.jpa.repositories;

import com.acme.procedurescafelab.calibrations.domain.model.aggregates.GrindCalibration;
import com.acme.procedurescafelab.shared.domain.model.valueobjects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrindCalibrationRepository extends JpaRepository<GrindCalibration, Long> {

    List<GrindCalibration> findByUserIdOrderByCreatedAtDesc(UserId userId);

    Optional<GrindCalibration> findByIdAndUserId(Long id, UserId userId);
}
