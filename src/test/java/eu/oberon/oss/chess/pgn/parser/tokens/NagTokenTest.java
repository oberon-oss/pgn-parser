package eu.oberon.oss.chess.pgn.parser.tokens;

import eu.oberon.oss.chess.pgn.parser.InvalidTokenValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NagTokenTest {
    public static Stream<Arguments> testValidNagTokens() {
        List<Arguments> arguments = new ArrayList<>();
        for (int i = 0; i <= 255; i++) {
            arguments.add(Arguments.of(i));
        }
        
        return arguments.stream();
    }

    @ParameterizedTest
    @MethodSource
    void testValidNagTokens(final int nagValue) {
        for (int i = 0; i < 255; i++) {
            assertDoesNotThrow(()->new NagToken("$"+nagValue));
        }
    }
    
    @Test
    void testInvalidNagTokens() {
        InvalidTokenValueException e = assertThrows(InvalidTokenValueException.class, () -> new NagToken("$256"));
        assertEquals("Invalid element value for element type NagToken: '256'", e.getMessage());
    }
}