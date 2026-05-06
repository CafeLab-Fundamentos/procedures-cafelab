package com.acme.procedurescafelab.defect.domain.services;

import com.acme.procedurescafelab.defect.domain.model.aggregates.Defect;
import com.acme.procedurescafelab.defect.domain.model.queries.GetDefectByIdForUserQuery;
import com.acme.procedurescafelab.defect.domain.model.queries.GetDefectsByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface DefectQueryService {

    List<Defect> handle(GetDefectsByUserIdQuery query);

    Optional<Defect> handle(GetDefectByIdForUserQuery query);
}
