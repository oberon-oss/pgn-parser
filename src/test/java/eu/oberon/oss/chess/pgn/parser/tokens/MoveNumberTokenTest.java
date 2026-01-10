package eu.oberon.oss.chess.pgn.parser.tokens;

import eu.oberon.oss.chess.pgn.parser.enums.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveNumberTokenTest {
    @Test
    void testValidWhiteMoveNumber() {
        MoveNumberToken token;
        
        token = assertDoesNotThrow(() -> new MoveNumberToken("1."));
        assertEquals(1, token.getTokenValue());
        assertEquals(Color.WHITE,token.getColorIndicator());
        assertEquals("1.", token.toString());

        token = assertDoesNotThrow(() -> new MoveNumberToken("1"));
        assertEquals(1, token.getTokenValue());
        assertEquals(Color.WHITE,token.getColorIndicator());
        assertEquals("1.", token.toString());


    }

    @Test
    void testValidBlackMoveNumber() {
        MoveNumberToken token;

        token = assertDoesNotThrow(() -> new MoveNumberToken("23..."));
        assertEquals(23, token.getTokenValue());
        assertEquals(Color.BLACK,token.getColorIndicator());
        assertEquals("23...", token.toString());
    }
}