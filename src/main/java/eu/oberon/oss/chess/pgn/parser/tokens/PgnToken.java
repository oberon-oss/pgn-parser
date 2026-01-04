package eu.oberon.oss.chess.pgn.parser.tokens;

/**
 * Represents a token from a PGN notated chess game.
 *
 * @param <T> The type parameter of the token value
 *
 * @author TigerLilly64
 * @since 1.0.0
 */
public interface PgnToken<T> {
    /**
     * Retrieves the value associated with the token
     *
     * @return The token value.
     *
     * @since 1.0.0
     */
    T getTokenValue();
}
