package eu.oberon.oss.chess.pgn.parser.utils;

import eu.oberon.oss.chess.pgn.parser.Piece;
import eu.oberon.oss.chess.pgn.parser.enums.Color;
import jakarta.annotation.Nonnull;

import static eu.oberon.oss.chess.pgn.parser.enums.Type.*;
import static eu.oberon.oss.chess.pgn.parser.enums.Type.BISHOP;
import static eu.oberon.oss.chess.pgn.parser.enums.Type.KNIGHT;
import static eu.oberon.oss.chess.pgn.parser.enums.Type.PAWN;
import static eu.oberon.oss.chess.pgn.parser.enums.Type.ROOK;

/**
 * Interface that allows the generation of a FEN string tag value from a user provided source object
 *
 * @param <S> The class type of the source object that can be processed into a FEN string when calling the
 *            {@link #generateFENString(Object)} method.
 *
 * @author TigerLilly64
 * @since 1.0.0
 */
public interface FENStringGenerator<S> {
    /**
     * Converts the provided source object into a FEN string.
     *
     * @param source The input data providing the details needed to create the FEN string.
     *
     * @return The FEN string that was generated.
     *
     * @since 1.0.0
     */
    String generateFENString(S source);

    /**
     * Converts the given piece into the correct FEN piece symbol
     *
     * @param piece the piece to convert into a FEN symbol
     *
     * @return The FEN symbol matching the 'piece' parameter
     *
     * @throws IllegalStateException if the provided piece type is not recognized.
     * @since 1.0.0
     */
    static char getFENSymbol(@Nonnull Piece piece) {
        return switch (piece.type()) {
            case KING -> piece.color() == Color.WHITE ? 'K' : 'k';
            case QUEEN -> piece.color() == Color.WHITE ? 'Q' : 'q';
            case ROOK -> piece.color() == Color.WHITE ? 'R' : 'r';
            case BISHOP -> piece.color() == Color.WHITE ? 'B' : 'b';
            case KNIGHT -> piece.color() == Color.WHITE ? 'N' : 'n';
            case PAWN -> piece.color() == Color.WHITE ? 'P' : 'p';
            case NO_PIECE ->  '\0';
        };
    }

    /**
     * Returns the appropriate {@link Piece} for the provided FEN symbol
     *
     * @param symbol The FEN symbol for which to return the Piece object.
     *
     * @return The associated Piece.
     *
     * @throws IllegalArgumentException if the value specified for the symbol parameter is not recognized.
     * @since 1.0.0
     */
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
            case '\0' -> Piece.EMPTY;
            default -> throw new IllegalArgumentException("Parameter 'symbol': value not recognized '" + symbol + "'");
        };
    }
}
