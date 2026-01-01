package eu.oberon.oss.chess.pgn.parser.tokens.tags;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class RatingTagToken extends StandardTagToken<Integer> {
    private static final Pattern VALID_RATING_PATTERN = Pattern.compile("(\\d+)");
    private static final Predicate<String> VALIDATOR = s -> VALID_RATING_PATTERN.matcher(s).matches();
    private static final Function<String, Integer> CONVERTER = Integer::parseInt;

    public RatingTagToken(String tagName, String tagValue) {
        super(tagName, tagValue, VALIDATOR, CONVERTER);
    }
}
