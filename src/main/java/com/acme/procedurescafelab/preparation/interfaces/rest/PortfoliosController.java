package com.acme.procedurescafelab.preparation.interfaces.rest;

import com.acme.procedurescafelab.preparation.domain.exceptions.PortfolioNotFoundException;
import com.acme.procedurescafelab.preparation.interfaces.acl.PreparationContextFacade;
import com.acme.procedurescafelab.preparation.interfaces.rest.resources.CreatePortfolioResource;
import com.acme.procedurescafelab.preparation.interfaces.rest.resources.UpdatePortfolioResource;
import com.acme.procedurescafelab.preparation.interfaces.rest.transform.CreatePortfolioCommandFromResourceAssembler;
import com.acme.procedurescafelab.preparation.interfaces.rest.transform.PortfolioResourceFromEntityAssembler;
import com.acme.procedurescafelab.preparation.interfaces.rest.transform.UpdatePortfolioCommandFromResourceAssembler;
import com.acme.procedurescafelab.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/portfolios", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Portfolios", description = "Portafolios por perfil (JWT)")
public class PortfoliosController {
    private final PreparationContextFacade preparationContextFacade;

    public PortfoliosController(PreparationContextFacade preparationContextFacade) {
        this.preparationContextFacade = preparationContextFacade;
    }

    @Operation(summary = "Crear portafolio (perfil desde JWT)")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPortfolio(@RequestBody CreatePortfolioResource resource) {
        try {
            var command =
                    CreatePortfolioCommandFromResourceAssembler.toCommand(resource);
            var portfolioId = preparationContextFacade.createPortfolio(
                    command.userId(), command.name());
            if (portfolioId == null || portfolioId == 0L) {
                return ResponseEntity.badRequest()
                        .body(new MessageResource("No se pudo crear el portafolio"));
            }
            var portfolio =
                    preparationContextFacade.getPortfolioByIdForUser(portfolioId, resource.userId());
            if (portfolio.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new MessageResource("No se pudo obtener el portafolio creado"));
            }
            var portfolioResource = PortfolioResourceFromEntityAssembler.toResourceFromEntity(portfolio.get());
            return new ResponseEntity<>(portfolioResource, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new MessageResource(ex.getMessage()));
        }
    }

    @Operation(summary = "Listar portafolios del perfil autenticado")
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> listMyPortfolios(@PathVariable Long userId) {
        var portfolios = preparationContextFacade.getPortfoliosByUserId(userId);
        var resources = portfolios.stream()
                .map(PortfolioResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Obtener portafolio por id (solo si pertenece al perfil)")
    @GetMapping("/user/{userId}/{portfolioId}")
    public ResponseEntity<?> getPortfolioById(
            @PathVariable Long userId,
            @PathVariable Long portfolioId
    ) {
        var portfolio = preparationContextFacade.getPortfolioByIdForUser(portfolioId, userId);
        if (portfolio.isEmpty()) {
            throw new PortfolioNotFoundException(portfolioId);
        }
        return ResponseEntity.ok(PortfolioResourceFromEntityAssembler.toResourceFromEntity(portfolio.get()));
    }

    @Operation(summary = "Actualizar portafolio (solo si pertenece al perfil)")
    @PutMapping(value = "/user/{userId}/{portfolioId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePortfolio(
            @PathVariable Long userId,
            @PathVariable Long portfolioId,
            @RequestBody UpdatePortfolioResource resource
    ) {
        try {
            var command = UpdatePortfolioCommandFromResourceAssembler.toCommandFromResource(
                    userId, portfolioId, resource);
            var updated = preparationContextFacade.updatePortfolio(command);
            if (updated.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MessageResource("No se pudo actualizar el portafolio"));
            }
            return ResponseEntity.ok(PortfolioResourceFromEntityAssembler.toResourceFromEntity(updated.get()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new MessageResource(ex.getMessage()));
        }
    }

    @Operation(summary = "Eliminar portafolio (solo si pertenece al perfil)")
    @DeleteMapping("/user/{userId}/{portfolioId}")
    public ResponseEntity<?> deletePortfolio(
            @PathVariable Long userId,
            @PathVariable Long portfolioId
    ) {
        boolean deleted = preparationContextFacade.deletePortfolio(portfolioId, userId);
        if (deleted) {
            return ResponseEntity.ok(new MessageResource("Portafolio eliminado exitosamente"));
        }
        throw new PortfolioNotFoundException(portfolioId);
    }
}
