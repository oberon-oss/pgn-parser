package eu.oberon.oss.chess.pgn.parser.tokens.tags;

import eu.oberon.oss.chess.pgn.parser.Piece;
import eu.oberon.oss.chess.pgn.parser.enums.Color;
import eu.oberon.oss.chess.pgn.parser.enums.Field;
import eu.oberon.oss.chess.pgn.parser.utils.FENStringGenerator;

import java.util.EnumMap;

import static eu.oberon.oss.chess.pgn.parser.enums.Type.NO_PIECE;

public class PGNFENTagStringGenerator implements FENStringGenerator<FENTagDetails> {
    
    @Override
    public String generateFENString(FENTagDetails source) {
        return writePositionData(source.getPosition()) +
                " " +
                (source.getSideToMove() == Color.WHITE ? "w" : "b") +
                " " +
                getCastlingRights(source) +
                " " +
                (source.getEnPassantField() == null ? "-" : source.getEnPassantField()) +
                " " + source.getPlyCount() +
                " " + source.getMoveCount();
    }

    private String getCastlingRights(FENTagDetails details) {
        StringBuilder sb = new StringBuilder();
        int currentLength = sb.length();
        sb.append(details.isWhiteCanCastleKingSide() ? "K" : "");
        sb.append(details.isWhiteCanCastleQueenSide() ? "Q" : "");
        sb.append(details.isBlackCanCastleKingSide() ? "k" : "");
        sb.append(details.isBlackCanCastleQueenSide() ? "q" : "");
        if (sb.length() == currentLength) {
            sb.append("-");
        }
        return sb.toString();
    }

    private String writePositionData(EnumMap<Field, Piece> position) {
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        int atRankPosition = 0;

        for (Field field : Field.values()) {
            if (atRankPosition == 8) {
                if (counter != 0) {
                    sb.append(counter);
                    counter = 0;
                }
                atRankPosition = 0;
                sb.append("/");
            }
            Piece piece = position.get(field);
            if (piece.type() == NO_PIECE) {
                counter++;
            } else {
                if (counter != 0) {
                    sb.append(counter);
                    counter = 0;
                }
                sb.append(FENStringGenerator.getFENSymbol(piece));
            }
            atRankPosition++;
        }
        return sb.toString();
    }
    
}
