package com.milaboratory.mir;

import com.milaboratory.core.Range;
import com.milaboratory.core.sequence.AminoAcidAlphabet;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.mappers.markup.PrecomputedSequenceRegionMarkup;

public final class SequenceUtils {
    private SequenceUtils() {

    }

    // TODO: can be moved to milib
    public static Range stickToBoundaries(Range range, Range parentRange) {
        return new Range(stickToBoundaries(range.getFrom(), parentRange),
                stickToBoundaries(range.getTo(), parentRange));
    }

    // TODO: can be moved to milib
    public static Range stickToBoundaries(int from, int to, Range parentRange) {
        return new Range(stickToBoundaries(from, parentRange),
                stickToBoundaries(to, parentRange));
    }

    public static boolean isNonCoding(AminoAcidSequence sequence) {
        for (int i = 0; i < sequence.size(); i++) {
            byte code = sequence.codeAt(i);
            if (code == AminoAcidAlphabet.STOP || code == AminoAcidAlphabet.INCOMPLETE_CODON)
                return true;
        }

        return false;
    }

    // TODO: can be moved to milib
    public static int stickToBoundaries(int pos, Range parentRange) {
        return Math.max(Math.min(pos, parentRange.getTo()), parentRange.getFrom());
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
                                                    boolean trimFivePrime, int start, int end) {
        int offset = (end - start) % 3;
        if (trimFivePrime) {
            start += offset;
        } else {
            end -= offset;
        }

        int startAa = start / 3;

        return start == end ? new Translation(startAa, startAa, AminoAcidSequence.EMPTY) :
                new Translation(startAa, end / 3,
                        AminoAcidSequence.translate(sequence.getRange(start, end)));
    }

    public static final class Translation {
        private final int start, end;
        private final AminoAcidSequence sequence;

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
