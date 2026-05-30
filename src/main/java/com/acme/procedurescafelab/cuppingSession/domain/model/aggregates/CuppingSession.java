package com.acme.procedurescafelab.cuppingSession.domain.model.aggregates;

import com.acme.procedurescafelab.cuppingSession.domain.model.commands.CreateCuppingSessionCommand;
import com.acme.procedurescafelab.cuppingSession.domain.model.commands.UpdateCuppingSessionCommand;
import com.acme.procedurescafelab.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.procedurescafelab.shared.domain.model.valueobjects.UserId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class CuppingSession extends AuditableAbstractAggregateRoot<CuppingSession> {

    @Embedded
    @Column(name = "user_id", nullable = false)
    private UserId userId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String origin;

    @Column(nullable = false, length = 255)
    private String variety;

    @Column(nullable = false, length = 120)
    private String processing;

    @Column(name = "session_date", nullable = false)
    private LocalDate sessionDate;

    @Column(nullable = false)
    private boolean favorite;

    @Column(name = "results_json", columnDefinition = "TEXT")
    private String resultsJson;

    @Column(name = "roast_style_notes", columnDefinition = "TEXT")
    private String roastStyleNotes;

    public CuppingSession() {}

    public CuppingSession(CreateCuppingSessionCommand c) {
        this.userId = new UserId(c.userId());
        this.name = c.name().trim();
        this.origin = c.origin().trim();
        this.variety = c.variety().trim();
        this.processing = c.processing().trim();
        this.sessionDate = c.sessionDate();
        this.favorite = c.favorite();
        this.resultsJson = blankToNull(c.resultsJson());
        this.roastStyleNotes = blankToNull(c.roastStyleNotes());
    }

    public void applyUpdate(UpdateCuppingSessionCommand c) {
        this.name = c.name().trim();
        this.origin = c.origin().trim();
        this.variety = c.variety().trim();
        this.processing = c.processing().trim();
        this.sessionDate = c.sessionDate();
        this.favorite = c.favorite();
        this.resultsJson = blankToNull(c.resultsJson());
        this.roastStyleNotes = blankToNull(c.roastStyleNotes());
    }

    private static String blankToNull(String s) {
        if (s == null || s.isBlank()) {
            return null;
        }
        return s.trim();
    }
}
