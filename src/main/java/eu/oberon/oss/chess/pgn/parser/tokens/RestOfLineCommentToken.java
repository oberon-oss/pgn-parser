package eu.oberon.oss.chess.pgn.parser.tokens;

import eu.oberon.oss.chess.pgn.parser.InvalidTokenValueException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestOfLineCommentToken implements PgnToken<String> {
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("^;(.*)");
    private final String comment;

    public RestOfLineCommentToken(String comment) {
        Matcher matcher = INSTRUCTION_PATTERN.matcher(comment);
        if (!matcher.matches()) {
            throw new InvalidTokenValueException(getClass().getSimpleName(), comment);
        }
        this.comment = matcher.group(1);
    }

    @Override
    public String getTokenValue() {
        return comment;
    }

    @Override
    public String toString() {
        return "%" + comment;
    }
}
