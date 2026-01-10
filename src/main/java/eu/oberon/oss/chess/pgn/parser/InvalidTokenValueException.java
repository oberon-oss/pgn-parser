package eu.oberon.oss.chess.pgn.parser;

public class InvalidTokenValueException extends RuntimeException {

    public <V> InvalidTokenValueException(String elementName, V elementValue) {
        super(String.format(
                        "Invalid element value for element type %s: %s",
                        elementName,
                        elementValue == null ? "<null>" : "'" + elementValue + "'"
                )
        );
    }

}
