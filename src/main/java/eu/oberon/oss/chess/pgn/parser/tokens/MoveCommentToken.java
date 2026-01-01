package eu.oberon.oss.chess.pgn.parser.tokens;

import eu.oberon.oss.chess.pgn.parser.InvalidTokenValueException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoveCommentToken implements PgnToken<String> {
    private static final Pattern MOVE_COMMENT_PATTERN = Pattern.compile("^\\{(.+)}$");
    private final String comment;

    public MoveCommentToken(String comment) {
        Matcher matcher = MOVE_COMMENT_PATTERN.matcher(comment);
        if (!matcher.matches()) {
            throw new InvalidTokenValueException("Move comment", comment);
        }

        this.comment = matcher.group(1);
    }

    @Override
    public String getTokenValue() {
        return comment;
    }

    @Override
    public String toString() {
        return "{" + comment + "}";
    }
}
