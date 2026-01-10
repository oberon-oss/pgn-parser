package eu.oberon.oss.chess.pgn.parser.tokens;

import eu.oberon.oss.chess.pgn.parser.InvalidTokenValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecursiveVariationTokenTest {

    @Test
    void testWithInvalidValue() {
        assertThrows(InvalidTokenValueException.class, () -> new RecursiveVariationToken(-1,false));
        assertThrows(InvalidTokenValueException.class, () -> new RecursiveVariationToken(-1,true));
    }
}