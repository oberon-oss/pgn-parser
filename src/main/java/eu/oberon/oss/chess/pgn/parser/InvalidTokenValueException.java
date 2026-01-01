package eu.oberon.oss.chess.pgn.parser;

public class InvalidTokenValueException extends RuntimeException {

    public InvalidTokenValueException(String elementName, String elementValue) {
        super(String.format(
                        "Invalid element value for element type %s: %s",
                        elementName,
                        elementValue == null ? "<null>" : "'" + elementValue + "'"
                )
        );
    }

    public InvalidTokenValueException(String elementName, String elementValue, String tagname) {
        super(String.format(
                        "Invalid element value for element type %s (%s): %s",
                        elementName,
                        tagname,
                        elementValue == null ? "<null>" : "'" + elementValue + "'"
                )
        );
    }
}
