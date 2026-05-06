package com.acme.procedurescafelab.calibrations.domain.services;

import com.acme.procedurescafelab.calibrations.domain.model.aggregates.GrindCalibration;
import com.acme.procedurescafelab.calibrations.domain.model.commands.CreateGrindCalibrationCommand;
import com.acme.procedurescafelab.calibrations.domain.model.commands.UpdateGrindCalibrationCommand;

import java.util.Optional;

public interface GrindCalibrationCommandService {

    Optional<GrindCalibration> handle(CreateGrindCalibrationCommand command);

    Optional<GrindCalibration> handle(UpdateGrindCalibrationCommand command);
}
