package eu.oberon.oss.chess.pgn.parser;

import eu.oberon.oss.chess.pgn.parser.PgnParseResult.PgnParseResultBuilder;
import eu.oberon.oss.chess.pgn.parser.tokens.*;
import eu.oberon.oss.chess.pgn.parser.tokens.tags.FENTagToken;
import eu.oberon.oss.chess.pgn.parser.tokens.tags.RatingTagToken;
import eu.oberon.oss.chess.pgn.parser.tokens.tags.StandardTagToken;
import generated.antlr.PGNFormatBaseListener;
import generated.antlr.PGNFormatParser;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

@Log4j2
class PgnProcessor extends PGNFormatBaseListener {
    private final PgnParseResultBuilder builder;

    @Getter
    private final ErrorHandler errorHandler;

    @Getter
    private PgnParseResult pgnParseResult = null;

    PgnProcessor(PgnParseResultBuilder builder) {
        this.builder = builder;
        errorHandler = new ErrorHandler(builder);
    }

    private long start;

    @Override
    public void enterParse(PGNFormatParser.ParseContext ctx) {
        start = LocalDateTime.now().getLong(ChronoField.MILLI_OF_DAY);
        super.enterParse(ctx);
    }

    //====================================== TAGS

    
    @Override
    public void exitTagPair(PGNFormatParser.TagPairContext ctx) {
        super.exitTagPair(ctx);
        try {
            // PGN tag values are enclosed in double quotes ("), strip these before processing the data.
            String tagValue = ctx.tagValue().getText().replace("\"", "");
            String tagName = ctx.tagName().getText().toLowerCase();
            switch (tagName) {
                case "fen" -> builder.addElement(new FENTagToken(tagValue));
                case "whiteelo", "blackelo", "whiteuscf", "blackuscf" ->
                        builder.addElement(new RatingTagToken(tagName, tagValue));
                default -> builder.addElement(new StandardTagToken<>(ctx.tagName().getText(), tagValue));
            }

        } catch (InvalidTokenValueException e) {

            builder.addMessage(PgnParseLogMessage
                    .builder()
                    .messageType(PgnParseLogMessage.MessageType.USER_DETECTED_EXCEPTION)
                    .message(e.getMessage())
                    .exception(e)
                    .line(ctx.tagValue().start.getLine())
                    .offset(ctx.tagValue().start.getCharPositionInLine())
                    .build()
            );
        }
    }
    //====================================== TAGS END

    //====================================== Move section
    private int recursionDepth = 0;

    @Override
    public void enterMoveTextSection(PGNFormatParser.MoveTextSectionContext ctx) {
        super.enterMoveTextSection(ctx);
        builder.addElement(new RecursiveVariationToken(recursionDepth, false));
    }

    @Override
    public void enterMoveNumberIndication(PGNFormatParser.MoveNumberIndicationContext ctx) {
        super.enterMoveNumberIndication(ctx);
        builder.addElement(new MoveNumberToken(ctx.getText()));
    }

    @Override
    public void exitSuffix(PGNFormatParser.SuffixContext ctx) {
        super.exitSuffix(ctx);
        LOGGER.info("SUFFIX: {}", ctx.getText());
    }

    @Override
    public void exitSanMove(PGNFormatParser.SanMoveContext ctx) {
        super.exitSanMove(ctx);
        builder.addElement(new SanMoveToken(ctx.getText()));
    }

    @Override
    public void enterRecursiveVariation(PGNFormatParser.RecursiveVariationContext ctx) {
        super.enterRecursiveVariation(ctx);
        builder.addElement(new RecursiveVariationToken(++recursionDepth, false));
    }

    @Override
    public void exitRecursiveVariation(PGNFormatParser.RecursiveVariationContext ctx) {
        super.exitRecursiveVariation(ctx);
        builder.addElement(new RecursiveVariationToken(recursionDepth--, true));
    }

    @Override
    public void exitMoveComment(PGNFormatParser.MoveCommentContext ctx) {
        super.enterMoveComment(ctx);
        builder.addElement(new MoveCommentToken(ctx.getText().replaceAll("\r?\n", " ")));
    }

    @Override
    public void exitNag(PGNFormatParser.NagContext ctx) {
        super.exitNag(ctx);
        builder.addElement(new NagToken(ctx.getText()));
    }

    @Override
    public void exitRestOfLineComment(PGNFormatParser.RestOfLineCommentContext ctx) {
        super.exitRestOfLineComment(ctx);
        builder.addElement(new RestOfLineCommentToken(ctx.getText()));
    }

    @Override
    public void exitProcessingInstruction(PGNFormatParser.ProcessingInstructionContext ctx) {
        super.exitProcessingInstruction(ctx);
        builder.addElement(new ProcessingInstructionToken(ctx.getText()));
    }

    @Override
    public void exitGameTermination(PGNFormatParser.GameTerminationContext ctx) {
        super.exitGameTermination(ctx);
        builder.addElement(new GameTerminationToken(ctx.getText()));
    }

    @Override
    public void exitMoveTextSection(PGNFormatParser.MoveTextSectionContext ctx) {
        super.exitMoveTextSection(ctx);
        builder.addElement(new RecursiveVariationToken(recursionDepth, true));
    }

    //====================================== Move section ENDS

    @Override
    public void exitParse(PGNFormatParser.ParseContext ctx) {
        super.exitParse(ctx);
        builder.setProcessingTime(LocalDateTime.now().getLong(ChronoField.MILLI_OF_DAY) - start);
        pgnParseResult = builder.build();
    }

}
