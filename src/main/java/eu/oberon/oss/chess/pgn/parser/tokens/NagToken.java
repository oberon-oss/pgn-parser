package eu.oberon.oss.chess.pgn.parser.tokens;

import eu.oberon.oss.chess.pgn.parser.InvalidTokenValueException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NagToken extends AbstractPgnToken<Integer> {
    private static final Pattern VALID_NAG_PATTERN = Pattern.compile("^\\$(\\d{1,3})$");

    public NagToken(String text) {
        Matcher matcher = VALID_NAG_PATTERN.matcher(text);
        super(text, s -> matcher.matches(), s -> Integer.parseInt(matcher.group(1)));
        if (getTokenValue() > 255) {
            throw new InvalidTokenValueException(getClass().getSimpleName(), "" + getTokenValue());
        }
    }

    @Override
    public String toString() {
        return "$" + getTokenValue();
    }
}
