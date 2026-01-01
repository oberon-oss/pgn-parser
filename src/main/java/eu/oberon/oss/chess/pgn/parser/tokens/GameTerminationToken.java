package eu.oberon.oss.chess.pgn.parser.tokens;

import eu.oberon.oss.chess.pgn.parser.InvalidTokenValueException;

import java.util.regex.Pattern;

public class GameTerminationToken implements PgnToken<String> {
    private static final Pattern GAME_TERMINATION_PATTERN = Pattern.compile("1-0|0-1|1/2-1/2|\\*"); 
    private final String gameTermination;

    public GameTerminationToken(String gameTermination) {
        if (!GAME_TERMINATION_PATTERN.matcher(gameTermination).matches()) {
            throw new InvalidTokenValueException(getClass().getSimpleName(),gameTermination);
        }
        this.gameTermination = gameTermination;
    }

    @Override
    public String getTokenValue() {
        return gameTermination;
    }
    
    @Override
    public String toString() {
        return getTokenValue();
    }
}
