package eu.oberon.oss.chess.pgn.parser.tokens;

import lombok.Getter;

public class RecursiveVariationToken extends AbstractPgnToken<Integer> {
    @Getter
    private final boolean isLeaving;

    public RecursiveVariationToken(int depth, boolean isLeaving) {
        super(depth, integer -> integer != null && integer >= 0);
        this.isLeaving = isLeaving;
    }

    @Override
    public String toString() {
        return (isLeaving ? "<===" : "===>") + " level " + getTokenValue();
    }
}
