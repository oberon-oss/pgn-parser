package eu.oberon.oss.chess.pgn.parser.tokens;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoveCommentToken extends AbstractPgnToken<String> {
    private static final Pattern MOVE_COMMENT_PATTERN = Pattern.compile("^\\{(.+)}$");

    public MoveCommentToken(String comment) {
        final Matcher matcher = MOVE_COMMENT_PATTERN.matcher(comment);
        super(comment, s -> matcher.matches(), s -> matcher.group(1));
    }

    @Override
    public String toString() {
        return "{" + getTokenValue() + "}";
    }
}
