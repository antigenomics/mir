package com.milaboratory.mir;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.GeneticCode;
import com.milaboratory.core.sequence.NucleotideSequence;
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

    public static Translation translateWithTrimming(NucleotideSequence sequence,
                                                    boolean trimFivePrime) {
        return translateWithTrimming(sequence, trimFivePrime, 0, sequence.size());
    }

    public static Translation translateWithTrimming(NucleotideSequence sequence,
                                                    boolean trimFivePrime, int start, int end) {
        int offset = (end - start) % 3;
        if (trimFivePrime) {
            start += offset;
        } else {
            end -= offset;
        }

        return start == end ? Translation.EMPTY :
                new Translation(start / 3, end / 3,
                        AminoAcidSequence.translate(sequence.getRange(start, end)));
    }

    public static final class Translation {
        private final int start, end;
        private final AminoAcidSequence sequence;

        public static Translation EMPTY = new Translation(-1, -1, AminoAcidSequence.EMPTY);

        public Translation(int start, int end, AminoAcidSequence sequence) {
            this.start = start;
            this.end = end;
            this.sequence = sequence;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public AminoAcidSequence getSequence() {
            return sequence;
        }

        public boolean isEmpty() {
            return sequence.size() == 0;
        }

        @Override
        public String toString() {
            return "Translation{" +
                    "start=" + start +
                    ", end=" + end +
                    ", sequence=" + sequence +
                    '}';
        }
    }
}
