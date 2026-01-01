package eu.oberon.oss.chess.pgn.parser.tokens.tags;

import eu.oberon.oss.chess.pgn.parser.Piece;
import eu.oberon.oss.chess.pgn.parser.enums.Color;
import eu.oberon.oss.chess.pgn.parser.enums.Field;
import lombok.Getter;

import java.util.EnumMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static eu.oberon.oss.chess.pgn.parser.enums.Type.NO_PIECE;
import static eu.oberon.oss.chess.pgn.parser.utils.FENStringGenerator.getPiece;

/**
 * Describes the characteristics of a chess position created from a PGN FEN Tag.
 *
 * @author TigerLilly64
 * @since 1.0.0
 */
@Getter
public class FENTagDetails {
    private static final FENTagStringGenerator STRING_GENERATOR = new FENTagStringGenerator();

    private static final Pattern VALID_FEN_PATTERN = Pattern.compile("((?:" +
            "[kqrbnpKQRBNP1-8]{1,8}/?){8})" // group 1: position
            + "\\s([bw])"                   // group 2: sideToMove
            + "\\s(-|[KQkq]{1,4})"          // group 3: castling rights
            + "\\s(-|[a-h][36])"            // group 4: en-passant field
            + "\\s(100|\\d{1,2})"           // group 5: half-move (ply) count
            + "\\s(\\d{1,3})"               // group 6: move count
    );

    private final boolean whiteCanCastleKingSide;
    private final boolean whiteCanCastleQueenSide;
    private final boolean blackCanCastleKingSide;
    private final boolean blackCanCastleQueenSide;
    //
    private final Color sideToMove;
    //
    private final Field enPassantField;

    private final int plyCount;
    private final int moveCount;

    private final EnumMap<Field, Piece> position;

    /**
     * Initializes the fen details from the provided FEN Tag input string.
     *
     * @param input The properly formatted FEN tag string to process.
     *
     * @throws IllegalArgumentException if the provided input string is not valid.
     * @since 1.0.0
     */
    public FENTagDetails(String input) {
        Matcher matcher = VALID_FEN_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalStateException();
        }

        position = processPieces(matcher.group(1));
        sideToMove = matcher.group(2).contentEquals("w") ? Color.WHITE : Color.BLACK;
        whiteCanCastleKingSide = matcher.group(3).contains("K");
        whiteCanCastleQueenSide = matcher.group(3).contains("Q");
        blackCanCastleKingSide = matcher.group(3).contains("k");
        blackCanCastleQueenSide = matcher.group(3).contains("q");
        enPassantField = matcher.group(4).contentEquals("-") ? null : Field.valueOf(matcher.group(4).toUpperCase());
        plyCount = Integer.parseInt(matcher.group(5));
        moveCount = Integer.parseInt(matcher.group(6));
    }

    /**
     * Validates if the provided FEN string is a valid FEN Tag string.
     * <p>
     * The FEN String is evaluated 'as-is'; the check performed is purely to make the assessment if the contents of the
     * provided string represents a possible valid FEN representation. It does not check for consistencies like the
     * number of pieces for either side, if there is exactly one king, if the chess position is actually legal/valid
     * etc.
     * <p>
     * Checks like these will need to be handled by code provided by the user; this lies outside of the scope of this
     * class.
     *
     * @param fenString The string to be examined.
     *
     * @return <b>True</b> if the string is compliant with the format of a FEN Tag string, or <b>false</b> otherwise.
     *
     * @since 1.0.0
     */
    public static boolean isValidFenString(String fenString) {
        return VALID_FEN_PATTERN.matcher(fenString).matches();
    }

    private EnumMap<Field, Piece> processPieces(String pieces) {
        final Field[] fieldList = Field.values();
        final EnumMap<Field, Piece> work = new EnumMap<>(Field.class);
        final List<Character> numerical = List.of('1', '2', '3', '4', '5', '6', '7', '8');

        int fieldIndex = 0;

        for (String rank : pieces.split("/")) {
            for (int i = 0; i < rank.length(); i++) {
                char c = rank.charAt(i);
                if (numerical.contains(c)) {
                    for (int j = 0; j < Character.getNumericValue(c); j++) {
                        work.put(fieldList[fieldIndex++], new Piece(NO_PIECE, null));
                    }
                } else {
                    Piece piece = getPiece(c);
                    work.put(fieldList[fieldIndex++], piece);
                }
            }
        }
        return work;
    }

    /**
     * Returns a valid FEN Tag string, based on the settings defined at construction time.
     *
     * @return A FEN formatted string representing the chess position details captured in this details instance.
     *
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return STRING_GENERATOR.generateFENString(this);
    }

}
