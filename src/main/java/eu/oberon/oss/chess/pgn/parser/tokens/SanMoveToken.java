package eu.oberon.oss.chess.pgn.parser.tokens;

import java.util.regex.Pattern;

public class SanMoveToken extends AbstractPgnToken<String> {

    private static final Pattern VALID_SAN_PATTERN = Pattern
            .compile("^(?:(" +
                            // Moves for regular pieces
                            "[KQRBN]" +     // Piece type
                            "[a-h1-8]?" +   // Optional: FROM (Disambiguation)
                            "x?" +          // Optional: capture
                            "[a-h][1-8]" +  // Target field
                            "[#+]?)" +      // Optional: Check or Checkmate indicator
                            // Pawn moves
                            "|(" +
                            "P?" +          // Allow for pawns to be identified by the letter 'P'
                            "[a-h1-8]?" +   // Optional: FROM (Disambiguation)
                            "x?" +          // Optional: capture
                            "[a-h][1-8]" +  // Target field
                            "(e.p|QRBN)?" + // Optional: En Passant capture or promotion to a Piece
                            "[#+]?)" +      // Optional: Check or Checkmate indicator
                            // Castling moves
                            "|(O-O(-O)?)" +
                            ")$",
                    Pattern.CASE_INSENSITIVE
            );

    public SanMoveToken(String sanMove) {
        super(sanMove, s -> VALID_SAN_PATTERN.matcher(sanMove).matches());
    }
}
