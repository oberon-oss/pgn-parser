package eu.oberon.oss.chess.pgn.parser;

import eu.oberon.oss.chess.pgn.parser.PgnParseResult.PgnParseResultBuilder;
import lombok.extern.log4j.Log4j2;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import java.util.BitSet;

@Log4j2
public class ErrorHandler implements ANTLRErrorListener {
    private final PgnParseResultBuilder builder;

    public ErrorHandler(PgnParseResultBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object o, int line, int offset, String additionalInformation, RecognitionException e) {
        builder.addMessage(
                PgnParseLogMessage.builder()
                                  .messageType(PgnParseLogMessage.MessageType.SYNTAX_ERROR)
                                  .message(additionalInformation)
                                  .line(line)
                                  .offset(offset)
                                  .exception(e)
                                  .build()
        );
    }

    @Override
    public void reportAmbiguity(Parser parser, DFA dfa, int line, int offset, boolean b, BitSet bitSet, ATNConfigSet atnConfigSet) {
        Token ct = parser.getCurrentToken();
        builder.addMessage(
                PgnParseLogMessage.builder()
                                  .messageType(PgnParseLogMessage.MessageType.REPORT_AMBIGUITY)
                                  .line(ct.getLine())
                                  .offset(ct.getCharPositionInLine())
                                  .build()
        );
    }

    @Override
    public void reportAttemptingFullContext(Parser parser, DFA dfa, int line, int offset, BitSet bitSet, ATNConfigSet atnConfigSet) {
        Token ct = parser.getCurrentToken();
        builder.addMessage(
                PgnParseLogMessage.builder()
                                  .messageType(PgnParseLogMessage.MessageType.REPORT_ATTEMPTING_FULL_CONTEXT)
                                  .line(ct.getLine())
                                  .offset(ct.getCharPositionInLine())
                                  .build()
        );
    }

    @Override
    public void reportContextSensitivity(Parser parser, DFA dfa, int line, int offset, int i2, ATNConfigSet atnConfigSet) {
        builder.addMessage(
                PgnParseLogMessage.builder()
                                  .messageType(PgnParseLogMessage.MessageType.REPORT_CONTEXT_SENSITIVITY)
                                  .line(line)
                                  .offset(offset)
                                  .build()
        );
    }
}
