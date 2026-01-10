package eu.oberon.oss.chess.pgn.parser;

import eu.oberon.oss.chess.pgn.parser.enums.Color;
import eu.oberon.oss.chess.pgn.parser.enums.Type;

public record Piece(Type type, Color color) {
    public static final Piece EMPTY = new Piece(Type.NO_PIECE, null) ;
}
