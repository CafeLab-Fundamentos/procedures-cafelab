package com.acme.procedurescafelab.cuppingSession.interfaces.rest;

import com.acme.procedurescafelab.cuppingSession.domain.exceptions.CuppingSessionNotFoundException;
import com.acme.procedurescafelab.cuppingSession.domain.model.queries.GetCuppingSessionByIdForUserQuery;
import com.acme.procedurescafelab.cuppingSession.domain.model.queries.GetCuppingSessionsByUserIdQuery;
import com.acme.procedurescafelab.cuppingSession.domain.services.CuppingSessionCommandService;
import com.acme.procedurescafelab.cuppingSession.domain.services.CuppingSessionQueryService;
import com.acme.procedurescafelab.cuppingSession.interfaces.rest.resources.CreateCuppingSessionResource;
import com.acme.procedurescafelab.cuppingSession.interfaces.rest.resources.UpdateCuppingSessionResource;
import com.acme.procedurescafelab.cuppingSession.interfaces.rest.transform.CreateCuppingSessionCommandFromResourceAssembler;
import com.acme.procedurescafelab.cuppingSession.interfaces.rest.transform.CuppingSessionResourceFromEntityAssembler;
import com.acme.procedurescafelab.cuppingSession.interfaces.rest.transform.UpdateCuppingSessionCommandFromResourceAssembler;
import com.acme.procedurescafelab.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/cupping-sessions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Cupping sessions", description = "Sesiones de cata por perfil")
public class CuppingSessionsController {
    private final CuppingSessionCommandService commandService;
    private final CuppingSessionQueryService queryService;

    public CuppingSessionsController(
            CuppingSessionCommandService commandService,
            CuppingSessionQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crear sesión de cata")
    public ResponseEntity<?> create(@RequestBody CreateCuppingSessionResource resource) {
        try {
            var cmd = CreateCuppingSessionCommandFromResourceAssembler.toCommand(resource);
            var created = commandService.handle(cmd);
            if (created.isEmpty()) {
                return ResponseEntity.badRequest().body(new MessageResource("No se pudo crear la sesión"));
            }
            return new ResponseEntity<>(
                    CuppingSessionResourceFromEntityAssembler.toResource(created.get()), HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new MessageResource(ex.getMessage()));
        }
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Listar sesiones del perfil (más recientes primero)")
    public ResponseEntity<?> list(@PathVariable Long userId) {
        var list = queryService.handle(new GetCuppingSessionsByUserIdQuery(userId));
        return ResponseEntity.ok(
                list.stream().map(CuppingSessionResourceFromEntityAssembler::toResource).collect(Collectors.toList()));
    }

    @GetMapping("user/{userId}/{sessionId}")
    @Operation(summary = "Obtener sesión por id")
    public ResponseEntity<?> getById(@PathVariable Long userId, @PathVariable Long sessionId) {
        var found = queryService.handle(new GetCuppingSessionByIdForUserQuery(sessionId, userId));
        if (found.isEmpty()) {
            throw new CuppingSessionNotFoundException(sessionId);
        }
        return ResponseEntity.ok(CuppingSessionResourceFromEntityAssembler.toResource(found.get()));
    }

    @PutMapping(value = "user/{userId}/{sessionId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualizar sesión")
    public ResponseEntity<?> update(
            @PathVariable Long userId,
            @PathVariable Long sessionId,
            @RequestBody UpdateCuppingSessionResource resource) {
        try {
            var cmd =
                    UpdateCuppingSessionCommandFromResourceAssembler.toCommand(
                            sessionId, userId, resource);
            var updated = commandService.handle(cmd);
            if (updated.isEmpty()) {
                throw new CuppingSessionNotFoundException(sessionId);
            }
            return ResponseEntity.ok(CuppingSessionResourceFromEntityAssembler.toResource(updated.get()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new MessageResource(ex.getMessage()));
        }
    }

    @DeleteMapping("user/{userId}/{sessionId}")
    @Operation(summary = "Eliminar sesión")
    public ResponseEntity<?> delete(@PathVariable Long userId, @PathVariable Long sessionId) {
        boolean ok = commandService.delete(sessionId, userId);
        if (!ok) {
            throw new CuppingSessionNotFoundException(sessionId);
        }
        return ResponseEntity.noContent().build();
    }
}
