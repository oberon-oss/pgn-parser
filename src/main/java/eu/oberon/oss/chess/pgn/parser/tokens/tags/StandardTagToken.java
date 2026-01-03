package eu.oberon.oss.chess.pgn.parser.tokens.tags;

import eu.oberon.oss.chess.pgn.parser.InvalidTokenValueException;
import eu.oberon.oss.chess.pgn.parser.tokens.PgnToken;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Default implementation for the {@link PgnToken} interface. It can be used to process PGN tags that are plain text
 * ({@link String} objects.
 * <p>
 * The class contains a protected constructor that can be used by subclasses that use a payload other than Strings.
 *
 * @param <T> Defines the type of object that is used to represent the payload.
 *
 * @author TigerLilly64
 * @since 1.0.0
 */
@Log4j2
public class StandardTagToken<T> implements PgnToken<T> {
    private static final Properties DEFAULT_PREFERRED_TAG_NAMES = new Properties();

    static {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("preferred_tag_names.properties")) {
            DEFAULT_PREFERRED_TAG_NAMES.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load default preferred tag names", e);
        }
    }

    private static final Pattern VALID_TAG_VALUE = Pattern.compile("^(.*)$");

    private static final Predicate<String> DEFAULT_VALIDATOR = string -> {
        Matcher matcher = VALID_TAG_VALUE.matcher(string);
        return matcher.matches() && !matcher.group(1).isEmpty();
    };

    private static final Function<String, ?> DEFAULT_CONVERTER = string -> {
        Matcher matcher = VALID_TAG_VALUE.matcher(string);
        if (!matcher.matches()) {
            throw new IllegalStateException();
        }
        return matcher.group(1);
    };

    @Getter
    private final String tagName;

    @Getter
    private final T tokenValue;

    /**
     * Creates a new PGN token that has a payload of type {@link String}
     *
     * @param tagName  The name of the tag to create.
     * @param tagValue The tag value to associate with the 'tagName'. The string should not be empty or contain white
     *                 space only.
     *
     * @throws IllegalArgumentException   If the tagName is empty or contains white space only.
     * @throws InvalidTokenValueException If the content of the tag value is empty, or contains white space only.
     * @since 1.0.0
     */
    public StandardTagToken(String tagName, String tagValue) {
        //noinspection unchecked
        this(tagName, tagValue, DEFAULT_VALIDATOR, (Function<String, T>) DEFAULT_CONVERTER);
    }

    /**
     * Constructor to be used by subclasses to
     *
     * @param tagName   The name of the tag to create.
     * @param tagValue  The tag value. This is an object of type {@code <T>}
     * @param validator The validator to use for checking the validity of the tagValue String
     * @param converter To convert the provided tag value into an object of type {@code <T>}.
     *
     * @throws IllegalArgumentException   If the tagName is empty or contains white space only.
     * @throws InvalidTokenValueException If the content of the tagValue is not acceptable to the validator
     * @since 1.0.0
     */
    protected StandardTagToken(String tagName, String tagValue, Predicate<String> validator, Function<String, T> converter) {
        if (tagName.isEmpty()) {
            throw new IllegalArgumentException("Invalid tag name: '" + tagName + "'");
        }

        String preferredName  = DEFAULT_PREFERRED_TAG_NAMES.getProperty(tagName.toLowerCase());
                
        if (!validator.test(tagValue)) {
            throw new InvalidTokenValueException(getClass().getSimpleName(), tagValue, tagName);
        }

        if (preferredName == null) {
            LOGGER.warn("No preferred formatted tag name found, using user provided name: {}", tagName);
        }
        this.tagName = preferredName == null ? tagName : preferredName;
        this.tokenValue = converter.apply(tagValue);
    }

    @Override
    public String toString() {
        return "[" + tagName + " \"" + tokenValue + "\"]";
    }
}
