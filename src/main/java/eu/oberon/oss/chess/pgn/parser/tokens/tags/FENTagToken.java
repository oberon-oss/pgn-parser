package eu.oberon.oss.chess.pgn.parser.tokens.tags;

import lombok.extern.log4j.Log4j2;

import java.util.function.Function;
import java.util.function.Predicate;

@Log4j2
public class FENTagToken extends StandardTagToken<FENTagDetails> {
    
    private static final Predicate<String> validator = FENTagDetails::isValidFenString;
    private static final Function<String, FENTagDetails> converter = FENTagDetails::new;

    public FENTagToken(String tagValue) {
        super("FEN", tagValue, validator, converter);
    }

}
