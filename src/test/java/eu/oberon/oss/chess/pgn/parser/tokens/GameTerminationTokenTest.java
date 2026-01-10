package eu.oberon.oss.chess.pgn.parser.tokens;

import eu.oberon.oss.chess.pgn.parser.InvalidTokenValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTerminationTokenTest {
    @Test
    void testValidTokenValues() {
        assertDoesNotThrow(()->new GameTerminationToken("0-1"));
        assertDoesNotThrow(()->new GameTerminationToken("1-0"));
        assertDoesNotThrow(()->new GameTerminationToken("1/2-1/2"));
        assertDoesNotThrow(()->new GameTerminationToken("*"));
    }
    
    @Test
    void testInvalidTokenValues() {
        assertThrows(InvalidTokenValueException.class, ()->new GameTerminationToken("??"));
    }
}