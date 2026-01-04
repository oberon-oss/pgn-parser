package eu.oberon.oss.chess.pgn.parser.tokens;

import eu.oberon.oss.chess.pgn.parser.InvalidTokenValueException;

import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractPgnToken<T> implements PgnToken<T> {
    private final T tokenValue;

    protected AbstractPgnToken(T tokenValue, Predicate<T> validator) {
        if (!validator.test(tokenValue)) {
            throw new InvalidTokenValueException(getClass().getSimpleName(), tokenValue);
        }
        this.tokenValue = tokenValue;
    }

    protected AbstractPgnToken(String tokenValue, Predicate<String> validator, Function<String, T> converter) {
        if (!validator.test(tokenValue)) {
            throw new InvalidTokenValueException(getClass().getSimpleName(), tokenValue);
        }

        this.tokenValue = converter.apply(tokenValue);
    }

    @Override
    public T getTokenValue() {
        return tokenValue;
    }

    @Override
    public String toString() {
        return tokenValue.toString();
    }
}
