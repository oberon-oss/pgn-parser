package eu.oberon.oss.chess.pgn.parser.tokens.tags;

import eu.oberon.oss.chess.pgn.parser.Chess960StartingPosition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PGNFENTagStringGeneratorTest {
    @Test
    void testWithInvalidIndexes() {
        assertThrows(IllegalArgumentException.class, ()->Chess960StartingPosition.getPosition(-1));
        assertThrows(IllegalArgumentException.class, ()->Chess960StartingPosition.getPosition(960));
    }
    
    static Stream<Arguments> testWithLegalStartingPositions() {
        List<Arguments> fenStrings = new ArrayList<>();
        for (int i = 0; i < Chess960StartingPosition.values().length; i++) {
            fenStrings.add(Arguments.of(Chess960StartingPosition.getFENString(i)));
        }
        
        return fenStrings.stream();
    }

    @ParameterizedTest
    @MethodSource
    void testWithLegalStartingPositions(String fenString) {
        FENTagDetails fenTagDetails1 = new FENTagDetails(fenString);
        FENTagDetails fenTagDetails2 = new FENTagDetails(fenTagDetails1.toString());
        assertEquals(fenTagDetails1,fenTagDetails2);
        assertTrue(fenTagDetails1.equals(fenTagDetails2) && fenTagDetails2.equals(fenTagDetails1));
        assertEquals(fenTagDetails1.hashCode(), fenTagDetails2.hashCode());
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
            "rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPP1PPP/RNBQKBNR w KQkq f6 0 1", // With en-passent
            "r1bqkbnr/pppp1ppp/2n5/4P3/4P3/8/PPP2PPP/RNBQKBNR b Qkq -  0 1", // No white castling KING side
            "r1bqkbnr/pppp1ppp/2n5/4P3/4P3/8/PPP2PPP/RNBQKBNR b Kkq -  0 1", // No white castling QUEEN side
            "r1bqkbnr/pppp1ppp/2n5/4P3/4P3/8/PPP2PPP/RNBQKBNR w  kq -  0 1", // No white castling KING or QUEEN side
            "r1bqkbnr/pppp1ppp/2n5/4P3/4P3/8/PPP2PPP/RNBQKBNR b KQq -  0 1", // No black castling KING side
            "r1bqkbnr/pppp1ppp/2n5/4P3/4P3/8/PPP2PPP/RNBQKBNR b KQK -  0 1", // No black castling QUEEN side
            "r1bqkbnr/pppp1ppp/2n5/4P3/4P3/8/PPP2PPP/RNBQKBNR w  KQ -  0 1", // No black castling KING or QUEEN side
            "r1bqkbnr/pppp1ppp/2n5/4P3/4P3/8/PPP2PPP/RNBQKBNR w  -  -  0 1"  // No black or white castling KING or QUEEN side
    })
    void testWithLegalNonStartingPositions(String fenPosition) {
        FENTagDetails fenTagDetails1 = new FENTagDetails(fenPosition);
        FENTagDetails fenTagDetails2 = new FENTagDetails(fenTagDetails1.toString());
        assertEquals(fenTagDetails1,fenTagDetails2);
        assertTrue(fenTagDetails1.equals(fenTagDetails2) && fenTagDetails2.equals(fenTagDetails1));
        assertEquals(fenTagDetails1.hashCode(), fenTagDetails2.hashCode());
    }
}