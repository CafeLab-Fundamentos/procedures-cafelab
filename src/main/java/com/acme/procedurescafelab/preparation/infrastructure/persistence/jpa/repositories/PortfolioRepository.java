package com.acme.procedurescafelab.preparation.infrastructure.persistence.jpa.repositories;

import com.acme.procedurescafelab.preparation.domain.model.aggregates.Portfolio;
import com.acme.procedurescafelab.shared.domain.model.valueobjects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByUserIdOrderByCreatedAtDesc(UserId userId);

    Optional<Portfolio> findByIdAndUserId(Long id, UserId userId);
}
