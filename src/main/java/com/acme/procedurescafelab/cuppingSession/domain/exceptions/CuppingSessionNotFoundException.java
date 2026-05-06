package com.acme.procedurescafelab.cuppingSession.domain.exceptions;

public class CuppingSessionNotFoundException extends RuntimeException {
    public CuppingSessionNotFoundException(Long sessionId) {
        super("Sesión de cata no encontrada");
    }
}
