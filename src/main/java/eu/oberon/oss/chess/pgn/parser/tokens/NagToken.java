package eu.oberon.oss.chess.pgn.parser.tokens;

import eu.oberon.oss.chess.pgn.parser.InvalidTokenValueException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NagToken implements PgnToken<Integer> {
    private static final  Pattern VALID_NAG_PATTERN = Pattern.compile("^\\$(\\d{1,3})$");
    private final int nagValue;
    
    public NagToken(String text) {
        Matcher matcher = VALID_NAG_PATTERN.matcher(text);
        if (!matcher.matches()) {
            throw new InvalidTokenValueException(getClass().getSimpleName(),text);
        }
        
        int tempNagValue = Integer.parseInt(matcher.group(1)); 
        if (tempNagValue > 255) {
            throw new InvalidTokenValueException(getClass().getSimpleName(), "" + tempNagValue);
        }
        
        this.nagValue = tempNagValue;
    }

    @Override
    public Integer getTokenValue() {
        return nagValue;
    }

    @Override
    public String toString() {
        return "$" + nagValue;
    }
}
