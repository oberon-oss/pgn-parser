package eu.oberon.oss.chess.pgn.parser;

import eu.oberon.oss.chess.pgn.parser.tokens.PgnToken;
import eu.oberon.oss.chess.pgn.parser.tokens.tags.StandardTagToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PgnParserTest {


    public static Stream<Arguments> testWithValidData() {
        return Stream.of(
                Arguments.of("valid-game-1.pgn", 446, 6),
                Arguments.of("valid-game-2.pgn", 88, 10)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testWithValidData(String resourceName, int expectedElements, int expectedTagCount) {
        PgnParseResult result;

        result = assertDoesNotThrow(() -> PgnParser.parsePGNData(loadTestData(resourceName), 1));
        assertNotNull(result);
        assertEquals(expectedElements, result.elements().size());

        int tagCount = 0;
        for (PgnToken<?> token : result.elements()) {
            if (token instanceof StandardTagToken<?>) {
                tagCount++;
            }
        }
        assertEquals(expectedTagCount, tagCount);
    }

    @Test
    void testWithInputDataAsNull() {
        assertThrows(NullPointerException.class, () -> PgnParser.parsePGNData(null));
    }

    @Test
    void testWithInputDataIsEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> PgnParser.parsePGNData(""));
    }

    @Test
    void testWithInputDataIsOnlyBlanks() {
        assertThrows(IllegalArgumentException.class, () -> PgnParser.parsePGNData("     "));
    }

    @Test
    void testWithInvalidStartingLine() {
        assertThrows(IllegalArgumentException.class, () -> PgnParser.parsePGNData("test-data", 0));
    }


    private String loadTestData(String resourceName) {
        try (InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(resourceName)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            return reader.readAllAsString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}