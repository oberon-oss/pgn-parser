package eu.oberon.oss.chess.pgn.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidTokenValueExceptionTest {

    @Test
    void validTokenValueException() {
        InvalidTokenValueException exception = assertDoesNotThrow(() -> new InvalidTokenValueException("Test-name", 
                "value"));
        assertEquals("Invalid element value for element type Test-name: 'value'", exception.getMessage());
    }

    @Test
    void invalidTokenValueException() {
        InvalidTokenValueException exception = assertDoesNotThrow(() -> new InvalidTokenValueException("Test-name", null));
        assertEquals("Invalid element value for element type Test-name: <null>", exception.getMessage());
    }
}