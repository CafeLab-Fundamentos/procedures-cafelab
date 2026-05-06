package com.acme.procedurescafelab.cuppingSession.interfaces.rest;

import com.acme.procedurescafelab.cuppingSession.domain.exceptions.CuppingSessionNotFoundException;
import com.acme.procedurescafelab.shared.interfaces.rest.resources.MessageResource;
import com.acme.procedurescafelab.shared.interfaces.rest.support.CafeLabScopedExceptionHandlerSupport;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.acme.procedurescafelab.cuppingSession.interfaces.rest")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CuppingSessionsExceptionHandler extends CafeLabScopedExceptionHandlerSupport {

    @ExceptionHandler(CuppingSessionNotFoundException.class)
    public ResponseEntity<MessageResource> handleNotFound(CuppingSessionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResource(ex.getMessage()));
    }
}
