package eu.oberon.oss.chess.pgn.parser.tokens;

import lombok.Getter;

public class RecursiveVariationToken implements PgnToken<Integer> {
    private final int depth;
    @Getter
    private final boolean isLeaving;

    public RecursiveVariationToken(int depth, boolean isLeaving) {
        this.depth = depth;
        this.isLeaving = isLeaving;
    }

    @Override
    public Integer getTokenValue() {
        return depth;
    }

    @Override
    public String toString() {
        return (isLeaving ? "<===" : "===>") + " level " + depth; 
    }
}
