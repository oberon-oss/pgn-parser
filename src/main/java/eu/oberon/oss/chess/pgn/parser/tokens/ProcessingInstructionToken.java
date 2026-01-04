package eu.oberon.oss.chess.pgn.parser.tokens;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessingInstructionToken extends AbstractPgnToken<String> {
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("^%(.*)");

    public ProcessingInstructionToken(String instruction) {
        final Matcher matcher = INSTRUCTION_PATTERN.matcher(instruction);
        super(instruction, s -> matcher.find(), s -> matcher.group(1)
        );
    }

    @Override
    public String toString() {
        return "%" + getTokenValue();
    }
}
