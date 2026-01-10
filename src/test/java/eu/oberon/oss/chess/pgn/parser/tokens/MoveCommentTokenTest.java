package eu.oberon.oss.chess.pgn.parser.tokens;

import eu.oberon.oss.chess.pgn.parser.InvalidTokenValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveCommentTokenTest {
    @Test
    void moveCommentToken() {
        MoveCommentToken token = assertDoesNotThrow(() -> new MoveCommentToken("{ this is a valid comment }"));
        assertEquals(" this is a valid comment ", token.getTokenValue());
        assertEquals("{ this is a valid comment }", token.toString());
        assertThrows(InvalidTokenValueException.class, () -> new MoveCommentToken("[ this is an invalid comment ]"));
    }
}