package eu.oberon.oss.chess.pgn.parser.utils;

import eu.oberon.oss.chess.pgn.parser.Piece;
import eu.oberon.oss.chess.pgn.parser.enums.Color;
import eu.oberon.oss.chess.pgn.parser.enums.Type;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FENStringGeneratorTest {
    public static Stream<Arguments> symbolToPiece() {
        return Stream.of(
                Arguments.of('k', new Piece(Type.KING, Color.BLACK)),
                Arguments.of('q', new Piece(Type.QUEEN, Color.BLACK)),
                Arguments.of('r', new Piece(Type.ROOK, Color.BLACK)),
                Arguments.of('b', new Piece(Type.BISHOP, Color.BLACK)),
                Arguments.of('n', new Piece(Type.KNIGHT, Color.BLACK)),
                Arguments.of('p', new Piece(Type.PAWN, Color.BLACK)),

                Arguments.of('K', new Piece(Type.KING, Color.WHITE)),
                Arguments.of('Q', new Piece(Type.QUEEN, Color.WHITE)),
                Arguments.of('R', new Piece(Type.ROOK, Color.WHITE)),
                Arguments.of('B', new Piece(Type.BISHOP, Color.WHITE)),
                Arguments.of('N', new Piece(Type.KNIGHT, Color.WHITE)),
                Arguments.of('P', new Piece(Type.PAWN, Color.WHITE)),
                
                Arguments.of('\0', Piece.EMPTY)
        );
    }

    @ParameterizedTest
    @MethodSource("symbolToPiece")
    void testSymbolToPiece(char symbol, Piece piece) {
        assertEquals(FENStringGenerator.getPiece(symbol), piece);
        assertEquals(symbol, FENStringGenerator.getFENSymbol(piece));
    }
    
    @Test
    void testInvalidSymbol() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> FENStringGenerator.getPiece('?'));
        assertEquals("Parameter 'symbol': value not recognized '?'", exception.getMessage());
    }
    
}