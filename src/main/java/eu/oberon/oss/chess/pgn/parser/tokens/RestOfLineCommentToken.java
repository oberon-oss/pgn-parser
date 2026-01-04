package eu.oberon.oss.chess.pgn.parser.tokens;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestOfLineCommentToken extends AbstractPgnToken<String> {
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("^;(.*)");

    public RestOfLineCommentToken(String comment) {
        final Matcher matcher = INSTRUCTION_PATTERN.matcher(comment);
        super(comment, s -> matcher.matches(), s -> matcher.group(1));
    }

    @Override
    public String toString() {
        return "%" + getTokenValue();
    }
}
 