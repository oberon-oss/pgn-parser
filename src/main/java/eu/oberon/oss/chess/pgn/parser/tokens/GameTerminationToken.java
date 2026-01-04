package eu.oberon.oss.chess.pgn.parser.tokens;

import java.util.regex.Pattern;

public class GameTerminationToken extends AbstractPgnToken<String> {
    private static final Pattern GAME_TERMINATION_PATTERN = Pattern.compile("1-0|0-1|1/2-1/2|\\*");

    public GameTerminationToken(String gameTermination) {
        super(gameTermination, s -> GAME_TERMINATION_PATTERN.matcher(gameTermination).matches());
    }

    @Override
    public String toString() {
        return getTokenValue();
    }
}
