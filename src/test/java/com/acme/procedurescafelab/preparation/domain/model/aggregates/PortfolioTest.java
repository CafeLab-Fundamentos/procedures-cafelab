package com.acme.procedurescafelab.preparation.domain.model.aggregates;

import com.acme.procedurescafelab.preparation.domain.model.commands.CreatePortfolioCommand;
import com.acme.procedurescafelab.preparation.domain.model.commands.UpdatePortfolioCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class PortfolioTest {

    @Test
    void createFromPrimitiveConstructorTest() {
        var portfolio = new Portfolio(8L, "Portafolio A");

        assertEquals(8L, portfolio.getUserId().userId());
        assertEquals("Portafolio A", portfolio.getName());
    }

    @Test
    void createFromCommandTest() {
        var portfolio = new Portfolio(new CreatePortfolioCommand(9L, "Home Bar"));

        assertEquals(9L, portfolio.getUserId().userId());
        assertEquals("Home Bar", portfolio.getName());
    }

    @Test
    void updateNameAndReturnSameInstanceTest() {
        var portfolio = new Portfolio(8L, "Inicial");

        var same = portfolio.update(new UpdatePortfolioCommand(8L, 1L, "Actualizado"));

        assertSame(portfolio, same);
        assertEquals("Actualizado", portfolio.getName());
    }

    @Test
    void haveNullIdBeforePersistenceTest() {
        assertNull(new Portfolio().getId());
    }
}
