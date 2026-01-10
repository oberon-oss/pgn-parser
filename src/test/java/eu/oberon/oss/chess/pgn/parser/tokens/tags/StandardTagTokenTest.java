package eu.oberon.oss.chess.pgn.parser.tokens.tags;

import eu.oberon.oss.chess.pgn.parser.InvalidTokenValueException;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class StandardTagTokenTest {
    
    @Test
    void testInvalidTagNames() {
        assertThrows(NullPointerException.class, ()-> new TestTag(null,"XXX"));
        assertThrows(IllegalArgumentException.class, ()-> new TestTag("","XXX"));
        assertThrows(IllegalArgumentException.class, ()-> new TestTag("   ","XXX"));
        
        assertThrows(NullPointerException.class, ()-> new TestTag(null,"XXX",null,null));
        assertThrows(IllegalArgumentException.class, ()-> new TestTag("","XXX",null,null));
        assertThrows(IllegalArgumentException.class, ()-> new TestTag("   ","XXX",null,null));
    }
    
    @Test
    void testValidTagNamesThatAreNotRecognized() {
        assertDoesNotThrow(()->new TestTag("SomeTagName","SomeTagValue"));
        assertDoesNotThrow(()->new TestTag("SomeTagName", "SomeTagValue", Objects::nonNull, s -> s));
    }

    @Test
    void testWithEmptyTagValues() {
        assertThrows(NullPointerException.class, ()-> new TestTag("ATagName",null));
        assertThrows(InvalidTokenValueException.class, ()-> new TestTag("ATagName",""));
        assertThrows(InvalidTokenValueException.class, ()-> new TestTag("ATagName","  "));
    }
    
    @Test
    void testValidTagNamesThatAreRecognized() {
        TestTag tag;
        tag = assertDoesNotThrow(() -> new TestTag("event", "SomeTagValue"));
        assertEquals("Event", tag.getTagName());
        assertEquals("[Event \"SomeTagValue\"]", tag.toString());
        
        tag = assertDoesNotThrow(()->new TestTag("event", "SomeTagValue", Objects::nonNull, s -> s));
        assertEquals("Event", tag.getTagName());
        assertEquals("[Event \"SomeTagValue\"]", tag.toString());
    }
    private static class TestTag extends  StandardTagToken<String> {
        protected TestTag(String tagName, String tagValue, Predicate<String> validator,
                          Function<String,String> converter) {
            super(tagName, tagValue, validator, converter);
        }

        public TestTag(String tagName, String tagValue) {
            super(tagName, tagValue);
        }
    }
}