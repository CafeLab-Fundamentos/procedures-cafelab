package com.acme.procedurescafelab.defect.interfaces.rest;

import com.acme.procedurescafelab.defect.domain.exceptions.DefectNotFoundException;
import com.acme.procedurescafelab.shared.interfaces.rest.resources.MessageResource;
import com.acme.procedurescafelab.shared.interfaces.rest.support.CafeLabScopedExceptionHandlerSupport;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.acme.procedurescafelab.defect.interfaces.rest")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DefectsExceptionHandler extends CafeLabScopedExceptionHandlerSupport {

    @ExceptionHandler(DefectNotFoundException.class)
    public ResponseEntity<MessageResource> handleDefectNotFound(DefectNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResource(ex.getMessage()));
    }
}
