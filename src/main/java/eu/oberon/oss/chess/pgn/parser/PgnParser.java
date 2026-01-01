package eu.oberon.oss.chess.pgn.parser;

import generated.antlr.PGNFormatLexer;
import generated.antlr.PGNFormatParser;
import jakarta.annotation.Nonnull;
import lombok.extern.log4j.Log4j2;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.List;

/**
 * Facilitates the parsing of PGN data.
 *
 * @author TigerLilly64
 * @since 1.0.0
 */
@Log4j2
public class PgnParser {
    /**
     * Creates a {@link PgnParseResult} object from the provided input.
     *
     * @param input        The input to process. This must be a string containing valid PGN data, i.e. consists of a tag
     *                     section, and at least a game termination token (1-0, 0-1 or 1/2-1/2).
     * @param startingLine The line to start counting with. This is used if the provided input is part of a larger data
     *                     set. It allows for properly reporting line numbers when problems are detected and reported.
     *                     The specified value must be {@literal  >= 1}, or an exception will be thrown.
     *
     * @return An object containing the outcome of the parse request.
     *
     * @throws IllegalArgumentException in either of the following cases:
     *                                  <ol>
     *                                      <li>The input data is empty (length 0), or contains white space only</li>
     *                                      <li>The starting line is {@literal  < 1}</li>
     *                                  </ol>
     * @since 1.0.0
     */
    public static PgnParseResult parsePGNData(@Nonnull String input, int startingLine) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("Invalid input data specified (empty or white-space only)");
        }
        if (startingLine < 1) {
            throw new IllegalArgumentException("Starting line number must be >= 1");
        }
        return new PgnParser(input, startingLine).parseInputData();
    }

    /**
     * Creates a {@link PgnParseResult} object from the provided input.
     * <p>
     * The line counting will start at line 1.
     *
     * @param input The input to process. This must be a string containing valid PGN data, i.e. consists of a tag
     *              section, and at least a game termination token (1-0, 0-1 or 1/2-1/2).*
     *
     * @return An object containing the outcome of the parse request.
     *
     * @since 1.0.0
     */
    public static PgnParseResult parsePGNData(String input) {
        return parsePGNData(input, 1);
    }

    private final String sourceData;
    private final PgnParseResult.PgnParseResultBuilder builder;

    private PgnParser(String sourceData, int startingLine) {
        this.sourceData = sourceData;
        builder = PgnParseResult.getBuilder(startingLine);
        builder.setInputData(List.of(sourceData.split("\n")));
    }

    private PgnParseResult parseInputData() {
        PgnProcessor pgnProcessor = new PgnProcessor(builder);

        Lexer lexer = new PGNFormatLexer(CharStreams.fromString(sourceData));
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

        PGNFormatParser parser = new PGNFormatParser(commonTokenStream);
        parser.removeErrorListeners();

        ParseTree parseTree = parser.parse();
        ParseTreeWalker walker = new ParseTreeWalker();

        walker.walk(pgnProcessor, parseTree);

        return pgnProcessor.getPgnParseResult();
    }

}
