package com.milaboratory.mir;

import com.milaboratory.core.sequence.Sequence;

public final class SequenceUtils {
    private SequenceUtils() {

    }

    public static <S extends Sequence<S>> S getSequenceRangeSafe(S seq, int from, int to) {
        int len = seq.size();

        return from > to ?
                seq.getAlphabet().getEmptySequence() :
                seq.getRange(from < 0 ? 0 : from, to > len ? len : to);
    }

    public static <S extends Sequence<S>> S getReverse(S seq) {
        var sb = seq.getBuilder();
        for (int i = seq.size() - 1; i >= 0; i--) {
            sb.append(seq.codeAt(i));
        }
        return sb.createAndDestroy();
    }
}
