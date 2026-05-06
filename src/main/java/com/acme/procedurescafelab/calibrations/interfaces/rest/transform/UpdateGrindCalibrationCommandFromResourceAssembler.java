package com.acme.procedurescafelab.calibrations.interfaces.rest.transform;

import com.acme.procedurescafelab.calibrations.domain.model.commands.UpdateGrindCalibrationCommand;
import com.acme.procedurescafelab.calibrations.interfaces.rest.resources.UpdateGrindCalibrationResource;

public class UpdateGrindCalibrationCommandFromResourceAssembler {

    public static UpdateGrindCalibrationCommand toCommand(
            Long calibrationId, Long userId, UpdateGrindCalibrationResource resource) {
        return new UpdateGrindCalibrationCommand(
                calibrationId,
                userId,
                resource.name(),
                resource.method(),
                resource.equipment(),
                resource.grindNumber(),
                resource.aperture(),
                resource.cupVolume(),
                resource.finalVolume(),
                resource.calibrationDate(),
                resource.comments(),
                resource.notes(),
                resource.sampleImage());
    }
}
