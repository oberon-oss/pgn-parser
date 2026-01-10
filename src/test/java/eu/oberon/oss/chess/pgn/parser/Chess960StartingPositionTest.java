package eu.oberon.oss.chess.pgn.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Chess960StartingPositionTest {
    @Test
    void testStartingPositions() {
        for (int i = 0; i < Chess960StartingPosition.values().length; i++) {
            Chess960StartingPosition position = Chess960StartingPosition.getPosition(i);
            assertEquals(i,position.getIndex());
        }
    }
}