package eu.oberon.oss.chess.pgn.parser.tokens.tags;

import eu.oberon.oss.chess.pgn.parser.Piece;
import org.junit.jupiter.api.Test;

import static eu.oberon.oss.chess.pgn.parser.enums.Color.BLACK;
import static eu.oberon.oss.chess.pgn.parser.enums.Color.WHITE;
import static eu.oberon.oss.chess.pgn.parser.enums.Field.*;
import static eu.oberon.oss.chess.pgn.parser.enums.Type.*;
import static org.junit.jupiter.api.Assertions.*;

class FENTagDetailsBuilderTest {

    @Test
    void testInvalidBuilderConfiguration() {
        FENTagDetails.FENTagDetailsBuilder builder = FENTagDetails.builder();
        assertThrows(IllegalStateException.class, builder::build);
    }

    @SuppressWarnings({"java:S5785"})
    @Test
    void testHash() {
        FENTagDetails.FENTagDetailsBuilder p1 = FENTagDetails.builder()
                .addPiece(A8, new Piece(ROOK, BLACK))
                .addPiece(E8, new Piece(KING, BLACK))
                .addPiece(H8, new Piece(ROOK, BLACK))
                .addPiece(A1, new Piece(ROOK, WHITE))
                .addPiece(E1, new Piece(KING, WHITE))
                .addPiece(H1, new Piece(ROOK, WHITE));

        FENTagDetails.FENTagDetailsBuilder p2 = FENTagDetails.builder()
                .addPiece(A8, new Piece(ROOK, BLACK))
                .addPiece(E8, new Piece(KING, BLACK))
                .addPiece(H8, new Piece(ROOK, BLACK))
                .addPiece(A1, new Piece(ROOK, WHITE))
                .addPiece(E1, new Piece(KING, WHITE))
                .addPiece(H1, new Piece(ROOK, WHITE));

        // White castling KING side
        p1.setWhiteCanCastleKingSide(true);
        assertNotEquals(p1.build().hashCode(), p2.build().hashCode());

        p2.setWhiteCanCastleKingSide(true);
        assertEquals(p1.build().hashCode(), p2.build().hashCode());

        // White castling KING side
        p1.setWhiteCanCastleQueenSide(true);
        assertNotEquals(p1.build().hashCode(), p2.build().hashCode());

        p2.setWhiteCanCastleQueenSide(true);
        assertEquals(p1.build().hashCode(), p2.build().hashCode());

        // Black castling KING side
        p1.setBlackCanCastleKingSide(true);
        assertNotEquals(p1.build().hashCode(), p2.build().hashCode());

        p2.setBlackCanCastleKingSide(true);
        assertEquals(p1.build().hashCode(), p2.build().hashCode());

        // Black castling KING side
        p1.setBlackCanCastleQueenSide(true);
        assertNotEquals(p1.build().hashCode(), p2.build().hashCode());

        p2.setBlackCanCastleQueenSide(true);
        assertEquals(p1.build().hashCode(), p2.build().hashCode());

        // PlyCount
        p1.setPlyCount(1);
        assertNotEquals(p1.build().hashCode(), p2.build().hashCode());

        p2.setPlyCount(1);
        assertEquals(p1.build().hashCode(), p2.build().hashCode());

        // MoveCount
        p1.setMoveCount(1);
        assertNotEquals(p1.build().hashCode(), p2.build().hashCode());

        p2.setMoveCount(1);
        assertEquals(p1.build().hashCode(), p2.build().hashCode());

        // Side to move
        p1.setSideToMove(BLACK);
        assertNotEquals(p1.build().hashCode(), p2.build().hashCode());

        p2.setSideToMove(BLACK);
        assertEquals(p1.build().hashCode(), p2.build().hashCode());

        p1.setSideToMove(WHITE);
        assertNotEquals(p1.build().hashCode(), p2.build().hashCode());

        p2.setSideToMove(WHITE);
        assertEquals(p1.build().hashCode(), p2.build().hashCode());

        // Position
        p1.addPiece(E3, new Piece(PAWN, WHITE));
        assertNotEquals(p1.build().hashCode(), p2.build().hashCode());

        p2.addPiece(E3, new Piece(PAWN, WHITE));
        assertEquals(p1.build().hashCode(), p2.build().hashCode());

        p1.addPiece(E6, new Piece(PAWN, BLACK));
        assertNotEquals(p1.build().hashCode(), p2.build().hashCode());

        p2.addPiece(E6, new Piece(PAWN, BLACK));
        assertEquals(p1.build().hashCode(), p2.build().hashCode());

        // en-passant
        p1.setEnPassantField(E3);
        assertNotEquals(p1.build().hashCode(), p2.build().hashCode());

        p2.setEnPassantField(E3);
        assertEquals(p1.build().hashCode(), p2.build().hashCode());

        p1.setEnPassantField(E6);
        assertNotEquals(p1.build().hashCode(), p2.build().hashCode());

        p2.setEnPassantField(E6);
        assertEquals(p1.build().hashCode(), p2.build().hashCode());
    }


    @SuppressWarnings({"java:S5785", "java:S5961", "SimplifiableAssertion", "ConstantValue", "EqualsBetweenInconvertibleTypes"})
    @Test
    void testEquals() {
        FENTagDetails.FENTagDetailsBuilder p1 = FENTagDetails.builder()
                .addPiece(A8, new Piece(ROOK, BLACK))
                .addPiece(E8, new Piece(KING, BLACK))
                .addPiece(H8, new Piece(ROOK, BLACK))
                .addPiece(A1, new Piece(ROOK, WHITE))
                .addPiece(E1, new Piece(KING, WHITE))
                .addPiece(H1, new Piece(ROOK, WHITE));

        FENTagDetails.FENTagDetailsBuilder p2 = FENTagDetails.builder()
                .addPiece(A8, new Piece(ROOK, BLACK))
                .addPiece(E8, new Piece(KING, BLACK))
                .addPiece(H8, new Piece(ROOK, BLACK))
                .addPiece(A1, new Piece(ROOK, WHITE))
                .addPiece(E1, new Piece(KING, WHITE))
                .addPiece(H1, new Piece(ROOK, WHITE));

        assertEquals(p1.build(), p2.build());
        assertFalse(p1.build().equals(null));
        assertFalse(p2.build().equals(null));

        assertFalse(p1.build().equals("p1"));
        assertFalse(p2.build().equals("p2"));

        // White castling KING side
        p1.setWhiteCanCastleKingSide(true);
        assertNotEquals(p1.build(), p2.build());

        p2.setWhiteCanCastleKingSide(true);
        assertEquals(p1.build(), p2.build());

        // White castling KING side
        p1.setWhiteCanCastleQueenSide(true);
        assertNotEquals(p1.build(), p2.build());

        p2.setWhiteCanCastleQueenSide(true);
        assertEquals(p1.build(), p2.build());

        // Black castling KING side
        p1.setBlackCanCastleKingSide(true);
        assertNotEquals(p1.build(), p2.build());

        p2.setBlackCanCastleKingSide(true);
        assertEquals(p1.build(), p2.build());

        // Black castling KING side
        p1.setBlackCanCastleQueenSide(true);
        assertNotEquals(p1.build(), p2.build());

        p2.setBlackCanCastleQueenSide(true);
        assertEquals(p1.build(), p2.build());

        // PlyCount
        p1.setPlyCount(1);
        assertNotEquals(p1.build(), p2.build());

        p2.setPlyCount(1);
        assertEquals(p1.build(), p2.build());

        // MoveCount
        p1.setMoveCount(1);
        assertNotEquals(p1.build(), p2.build());

        p2.setMoveCount(1);
        assertEquals(p1.build(), p2.build());

        // Side to move
        p1.setSideToMove(BLACK);
        assertNotEquals(p1.build(), p2.build());

        p2.setSideToMove(BLACK);
        assertEquals(p1.build(), p2.build());

        p1.setSideToMove(WHITE);
        assertNotEquals(p1.build(), p2.build());

        p2.setSideToMove(WHITE);
        assertEquals(p1.build(), p2.build());

        // Position
        p1.addPiece(E3, new Piece(PAWN, WHITE));
        assertNotEquals(p1.build(), p2.build());

        p2.addPiece(E3, new Piece(PAWN, WHITE));
        assertEquals(p1.build(), p2.build());

        p1.addPiece(E6, new Piece(PAWN, BLACK));
        assertNotEquals(p1.build(), p2.build());

        p2.addPiece(E6, new Piece(PAWN, BLACK));
        assertEquals(p1.build(), p2.build());

        // en-passant
        p1.setEnPassantField(E3);
        assertNotEquals(p1.build(), p2.build());

        p2.setEnPassantField(E3);
        assertEquals(p1.build(), p2.build());

        p1.setEnPassantField(E6);
        assertNotEquals(p1.build(), p2.build());

        p2.setEnPassantField(E6);
        assertEquals(p1.build(), p2.build());
    }

}
