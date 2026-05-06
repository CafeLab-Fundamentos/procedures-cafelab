package com.acme.procedurescafelab.calibrations.domain.exceptions;

public class GrindCalibrationNotFoundException extends RuntimeException {
    public GrindCalibrationNotFoundException(Long calibrationId) {
        super("Calibración no encontrada");
    }
}
