package eu.oberon.oss.chess.pgn.parser.tokens;

import eu.oberon.oss.chess.pgn.parser.InvalidTokenValueException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoveNumberToken implements PgnToken<Integer> {
    private static final Pattern MOVE_NUMBER_PATTERN = Pattern.compile("^(\\d+)(\\.{0,3})$");
    private final Integer tagValue;
    private final boolean indicatesBlackMove;

    public MoveNumberToken(String tagValue) {
        Matcher matcher = MOVE_NUMBER_PATTERN.matcher(tagValue);
        if (!matcher.matches()) {
            throw new InvalidTokenValueException("Move number", tagValue);
        }
        indicatesBlackMove = matcher.group(1) == null || matcher.group(1).length() > 1 ;
        this.tagValue = Integer.parseInt(matcher.group(1));
    }

    @Override
    public Integer getTokenValue() {
        return tagValue;
    }

    @Override
    public String toString() {
        return (indicatesBlackMove ? "..." + tagValue : tagValue + ".");
    }
}
