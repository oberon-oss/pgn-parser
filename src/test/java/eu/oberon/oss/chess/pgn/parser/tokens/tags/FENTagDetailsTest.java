package eu.oberon.oss.chess.pgn.parser.tokens.tags;

import eu.oberon.oss.chess.pgn.parser.Chess960StartingPosition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FENTagDetailsTest {

    @ParameterizedTest
    @EnumSource(Chess960StartingPosition.class)
    void testFENStrings(Chess960StartingPosition startingPositions) {
        assertTrue(FENTagDetails.isValidFenString(startingPositions.getFenString()));
    }

// INVALID_SETUP_STRING                             
// INVALID_SETUP_STRING_INVALID_RANK                
// POSTION_AFTER_1_WHITE_MOVE                       
// POSITION_SETUP_STRING_NO_CASTLING                
// EN_PASSANT_WITH_EMPTY_FIELD                      
// EN_PASSANT_WHITE_WITH_WHITE_PAWN                 
// EN_PASSANT_WHITE_WITH_BLACK_PAWN                 
// EN_PASSANT_BLACK_WITH_WHITE_PAWN                 
// EN_PASSANT_BLACK_WITH_BLACK_PAWN                 
// CASTLING_BLACK_KING_SIDE_NOT_ON_8TH_RANK         
// CASTLING_BLACK_KING_SIDE_ROOK_ON_INCORRECT_SIDE  
// CASTLING_BLACK_QUEEN_SIDE_ROOK_ON_INCORRECT_SIDE 
// CASTLING_WHITE_KING_SIDE_NOT_ON_1ST_RANK         

    public static Stream<Arguments> testInvalidFENString() {
        return Stream.of(
                //@formatter:off
                Arguments.of("rnbqkbnr/pppppppx/9/8/8/8/PPPPPPPP/RNBQKBNR   w QKqk -  0 0",false),
                Arguments.of("rnbqkbnr/ppppppp222/9/8/8/8/PPPPPPPP/RNBQKBNR w QKqk -  0 0",false),
                Arguments.of("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq -  8 1",true),
                Arguments.of("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR   w -    e3 1 8",true),
                Arguments.of("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR   w QKqk e7 0 0",false),
                Arguments.of("rnbqkbnr/pppppppp/4P3/8/8/8/PPPPPPPP/RNBQKBNR w QKqk e8 0 0",false),
                Arguments.of("rnbqkbnr/pppppppp/4p3/8/8/8/PPPPPPPP/RNBQKBNR w QKqk e5 0 0",false),
                Arguments.of("rnbqkbnr/pppppppp/8/8/8/4P3/PPPPPPPP/RNBQKBNR b QKqk e1 0 0",false),
                Arguments.of("rnbqkbnr/pppppppp/8/8/8/4p3/PPPPPPPP/RNBQKBNR b QKqk e2 0 0",false),
                Arguments.of("1nbq1bn1/rpppkppr/8/8/8/8/PPPPPPPP/RNBQKBNR   w k    -  3 2",true),
                Arguments.of("rnbqkbnq/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR   b q    -  1 0",true),
                Arguments.of("qnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR   w Q    -  0 1",true),
                Arguments.of("rnbqkbnr/pppppppp/8/8/8/8/RPPPKPPP/1NBQ1BN1   b K    -  4 9",true)
                //@formatter:on
        );
    }

    @ParameterizedTest
    @MethodSource
    void testInvalidFENString(String fenString, boolean expectedValid) {
        if (expectedValid) {
            assertTrue(() -> FENTagDetails.isValidFenString(fenString));
            assertDoesNotThrow(()-> new FENTagDetails(fenString));
        } else {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new FENTagDetails(fenString));
            assertEquals("Parameter 'input': Invalid FEN String '" + fenString + "'", exception.getMessage());
        }
    }
    
}