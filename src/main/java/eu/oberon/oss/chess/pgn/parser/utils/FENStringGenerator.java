package eu.oberon.oss.chess.pgn.parser.utils;

import eu.oberon.oss.chess.pgn.parser.Piece;
import eu.oberon.oss.chess.pgn.parser.enums.Color;

import static eu.oberon.oss.chess.pgn.parser.enums.Type.*;
import static eu.oberon.oss.chess.pgn.parser.enums.Type.BISHOP;
import static eu.oberon.oss.chess.pgn.parser.enums.Type.KNIGHT;
import static eu.oberon.oss.chess.pgn.parser.enums.Type.PAWN;
import static eu.oberon.oss.chess.pgn.parser.enums.Type.ROOK;

public interface FENStringGenerator<S> {
    String generateFENString(S source);

    static char getFENSymbol(Piece piece) {
        return switch (piece.type()) {
            case KING -> piece.color() == Color.WHITE ? 'K' : 'k';
            case QUEEN -> piece.color() == Color.WHITE ? 'Q' : 'q';
            case ROOK -> piece.color() == Color.WHITE ? 'R' : 'r';
            case BISHOP -> piece.color() == Color.WHITE ? 'B' : 'b';
            case KNIGHT -> piece.color() == Color.WHITE ? 'N' : 'n';
            case PAWN -> piece.color() == Color.WHITE ? 'P' : 'p';
            default -> '\0';
        };
    }

    static Piece getPiece(char symbol) {
        return switch (symbol) {
            case 'k' -> new Piece(KING, Color.BLACK);
            case 'K' -> new Piece(KING, Color.WHITE);
            case 'q' -> new Piece(QUEEN, Color.BLACK);
            case 'Q' -> new Piece(QUEEN, Color.WHITE);
            case 'r' -> new Piece(ROOK, Color.BLACK);
            case 'R' -> new Piece(ROOK, Color.WHITE);
            case 'b' -> new Piece(BISHOP, Color.BLACK);
            case 'B' -> new Piece(BISHOP, Color.WHITE);
            case 'n' -> new Piece(KNIGHT, Color.BLACK);
            case 'N' -> new Piece(KNIGHT, Color.WHITE);
            case 'p' -> new Piece(PAWN, Color.BLACK);
            case 'P' -> new Piece(PAWN, Color.WHITE);
            default -> throw new IllegalStateException("Unexpected value: " + symbol);
        };
    }
}
