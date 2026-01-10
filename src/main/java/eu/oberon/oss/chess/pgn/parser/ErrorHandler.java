package eu.oberon.oss.chess.pgn.parser;

import eu.oberon.oss.chess.pgn.parser.PgnParseResult.PgnParseResultBuilder;
import lombok.extern.log4j.Log4j2;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

@Log4j2
public class ErrorHandler extends  BaseErrorListener {
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
}
