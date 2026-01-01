package eu.oberon.oss.chess.pgn.parser.tokens;

import eu.oberon.oss.chess.pgn.parser.InvalidTokenValueException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessingInstructionToken implements PgnToken<String> {
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("^%(.*)");
    private final String instruction;

    public ProcessingInstructionToken(String instruction) {
        Matcher matcher = INSTRUCTION_PATTERN.matcher(instruction);
        if (!matcher.matches()) {
            throw new InvalidTokenValueException(getClass().getSimpleName(), instruction);
        }
        this.instruction = matcher.group(1);
    }

    @Override
    public String getTokenValue() {
        return instruction;
    }

    @Override
    public String toString() {
        return "%" + instruction;
    }
}
