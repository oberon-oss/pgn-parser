package eu.oberon.oss.chess.pgn.parser;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PgnParseLogMessage {

    public enum MessageType {
        REPORT_AMBIGUITY, 
        REPORT_ATTEMPTING_FULL_CONTEXT, 
        REPORT_CONTEXT_SENSITIVITY, 
        SYNTAX_ERROR, 
        USER_DETECTED_EXCEPTION,
        USER_NOTIFICATION
    }

    private final LocalDateTime timeRecorded = LocalDateTime.now();
    
    private final MessageType messageType;
    private final int line;
    private final int offset;
    private final String message;
    private final Exception exception;
}
