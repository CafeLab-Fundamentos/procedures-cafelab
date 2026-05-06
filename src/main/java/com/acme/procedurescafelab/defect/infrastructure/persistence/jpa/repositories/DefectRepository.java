package com.acme.procedurescafelab.defect.infrastructure.persistence.jpa.repositories;

import com.acme.procedurescafelab.defect.domain.model.aggregates.Defect;
import com.acme.procedurescafelab.shared.domain.model.valueobjects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DefectRepository extends JpaRepository<Defect, Long> {

    List<Defect> findByUserIdOrderByCreatedAtDesc(UserId userId);

    Optional<Defect> findByIdAndUserId(Long id, UserId userId);
}
