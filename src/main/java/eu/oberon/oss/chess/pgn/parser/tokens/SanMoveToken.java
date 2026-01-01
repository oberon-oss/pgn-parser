package eu.oberon.oss.chess.pgn.parser.tokens;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SanMoveToken implements PgnToken<String> {

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
    private final String sanMove;

    public SanMoveToken(String sanMove) {
        Matcher matcher = VALID_SAN_PATTERN.matcher(sanMove);
        if (!matcher.matches()) {
            LOGGER.warn("SAN move not recognized: {}", sanMove);
        }
        this.sanMove = sanMove;
    }

    @Override
    public String getTokenValue() {
        return sanMove;
    }

    @Override
    public String toString() {
        return getTokenValue();
    }
}
