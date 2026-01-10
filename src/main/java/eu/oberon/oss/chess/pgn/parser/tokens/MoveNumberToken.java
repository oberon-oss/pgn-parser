package eu.oberon.oss.chess.pgn.parser.tokens;

import eu.oberon.oss.chess.pgn.parser.enums.Color;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoveNumberToken extends AbstractPgnToken<Integer> {
    private static final Pattern MOVE_NUMBER_PATTERN = Pattern.compile("^(\\d+)(\\.{1,3})?$");
    private final boolean indicatesBlackMove;

    public MoveNumberToken(String tagValue) {
        final Matcher matcher = MOVE_NUMBER_PATTERN.matcher(tagValue);
        super(tagValue, s -> matcher.matches(), s -> Integer.parseInt(matcher.group(1)));
        indicatesBlackMove = matcher.group(2) != null && matcher.group(2).length() > 1;
    }

    public Color getColorIndicator() {
        return indicatesBlackMove ? Color.BLACK : Color.WHITE;
    }
    
    @Override
    public String toString() {
        return (getTokenValue() + (indicatesBlackMove ? "..." : "."));
    }
}
