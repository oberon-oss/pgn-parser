package eu.oberon.oss.chess.pgn.parser;

import eu.oberon.oss.chess.pgn.parser.tokens.PgnToken;
import jakarta.annotation.Nonnull;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Records the result of parsing a single PGN game.
 *
 * @param elements       The tokens extracted from the input source. If successful, it will contain all tags and items
 *                       from the move-text section, in the order in which they were parsed.
 * @param messages       The record messages. These can be errors detected by the parser itself, but also contain user
 *                       provided exception messages
 * @param inputData      The string representation of the original data that was parsed.
 * @param startingLine   The starting line used when parsing. This is to correctly number the lines, for those cases
 *                       where the game that was parsed originates from a source that contains multiple PGN games
 * @param processingTime The amount of took to parse the PGN data.
 *
 * @author TigerLilly64
 * @since 1.0.0
 */
@Log4j2
public record PgnParseResult(List<PgnToken<?>> elements, List<PgnParseLogMessage> messages, List<String> inputData,
                             int startingLine, long processingTime) {

    /**
     * Returns a new builder for a PgnParseResult object.
     *
     * @param startingLine The starting line of the data to parse, relative to the start of the source the pgn data
     *                     originated from. The line number will be used to correctly identify the line(s) where
     *                     messages where reported for.
     *
     * @return The builder instance.
     *
     * @since 1.0.0
     */
    static PgnParseResultBuilder getBuilder(int startingLine) {
        return new PgnParseResultBuilder(startingLine);
    }

    /**
     * A builder class to construct {@link PgnParseResult} objects.
     *
     * @author TigerLilly64
     * @since 1.0.0
     */
    public static class PgnParseResultBuilder {
        private final List<PgnToken<?>> elements;
        private final List<PgnParseLogMessage> messages;
        private final List<String> inputData;
        private final int startingLine;
        @Setter
        private long processingTime = 0;

        private PgnParseResultBuilder(int startingLine) {
            this.startingLine = startingLine;
            elements = new LinkedList<>();
            messages = new ArrayList<>();
            inputData = new ArrayList<>();
        }

        /**
         * Sets the source data that was parsed.
         *
         * @param inputData The PGN input data.
         *
         * @since 1.0.0
         */
        public void setInputData(@Nonnull List<String> inputData) {
            this.inputData.addAll(inputData);
        }

        /**
         * Adds a pgn token element to the element list.
         *
         * @param element The element to add.
         *
         * @since 1.0.0
         */
        public void addElement(@Nonnull PgnToken<?> element) {
            elements.add(element);
            LOGGER.info("Added element {}: {} ({})", String.format("%5d", elements.size()), element.getClass().getSimpleName(), element);
        }

        /**
         * Adds a message to the message list.
         *
         * @param message The message to add.
         *
         * @since 1.0.0
         */
        public void addMessage(PgnParseLogMessage message) {
            messages.add(message);
        }

        public PgnParseResult build() {
            return new PgnParseResult(elements, messages, inputData, startingLine, processingTime);
        }
    }
}
