package com.acme.procedurescafelab.defect.interfaces.rest;

import com.acme.procedurescafelab.defect.domain.exceptions.DefectNotFoundException;
import com.acme.procedurescafelab.defect.domain.model.queries.GetDefectByIdForUserQuery;
import com.acme.procedurescafelab.defect.domain.model.queries.GetDefectsByUserIdQuery;
import com.acme.procedurescafelab.defect.domain.services.DefectCommandService;
import com.acme.procedurescafelab.defect.domain.services.DefectQueryService;
import com.acme.procedurescafelab.defect.interfaces.rest.resources.CreateDefectResource;
import com.acme.procedurescafelab.defect.interfaces.rest.transform.CreateDefectCommandFromResourceAssembler;
import com.acme.procedurescafelab.defect.interfaces.rest.transform.DefectResourceFromEntityAssembler;
import com.acme.procedurescafelab.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/defects", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Defects", description = "Registro de defectos (snapshot de café + defecto)")
public class DefectsController {
    private final DefectCommandService defectCommandService;
    private final DefectQueryService defectQueryService;

    public DefectsController(
            DefectCommandService defectCommandService,
            DefectQueryService defectQueryService) {
        this.defectCommandService = defectCommandService;
        this.defectQueryService = defectQueryService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crear registro de defecto (userId desde JWT)")
    public ResponseEntity<?> createDefect(@RequestBody CreateDefectResource resource) {
        try {
            var command = CreateDefectCommandFromResourceAssembler.toCommandFromResource(resource);
            var defect = defectCommandService.handle(command);
            if (defect.isEmpty()) {
                return ResponseEntity.badRequest().body(new MessageResource("No se pudo crear el defecto"));
            }
            var defectResource = DefectResourceFromEntityAssembler.toResourceFromEntity(defect.get());
            return new ResponseEntity<>(defectResource, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new MessageResource(ex.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar defectos de un usuario por id")
    public ResponseEntity<?> getDefectsForUser(@PathVariable Long userId) {
        var defects = defectQueryService.handle(new GetDefectsByUserIdQuery(userId));
        var resources = defects.stream()
                .map(DefectResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/user/{userId}/{defectId}")
    @Operation(summary = "Obtener defecto por id (solo si pertenece al usuario indicado)")
    public ResponseEntity<?> getDefectById(@PathVariable Long userId, @PathVariable Long defectId) {
        var defect = defectQueryService.handle(new GetDefectByIdForUserQuery(defectId, userId));
        if (defect.isEmpty()) {
            throw new DefectNotFoundException(defectId);
        }
        return ResponseEntity.ok(DefectResourceFromEntityAssembler.toResourceFromEntity(defect.get()));
    }
}
