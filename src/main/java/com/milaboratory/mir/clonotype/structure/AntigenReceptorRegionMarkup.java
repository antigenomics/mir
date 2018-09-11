package com.milaboratory.mir.clonotype.structure;

import com.milaboratory.core.sequence.Sequence;

import java.util.Arrays;

import static com.milaboratory.mir.clonotype.structure.ReceptorRegionType.*;

public class AntigenReceptorRegionMarkup<S extends Sequence<S>> {
    private final int[] markup;

    public AntigenReceptorRegionMarkup(int fr1Start, int cdr1Start,
                                       int fr2Start, int cdr2Start,
                                       int fr3Start, int cdr3Start,
                                       int fr4Start, int fr4End) {
        this.markup = new int[]{
                fr1Start, cdr1Start,
                fr2Start, cdr2Start,
                fr3Start, cdr3Start,
                fr4Start, fr4End
        };
        if (cdr1Start >= 0 & fr1Start > cdr1Start ||
                fr2Start >= 0 & cdr1Start > fr2Start ||
                cdr2Start >= 0 & fr2Start > cdr2Start ||
                fr3Start >= 0 & cdr2Start > fr3Start ||
                cdr3Start >= 0 & fr3Start > cdr3Start ||
                fr4Start >= 0 & cdr3Start > fr4Start ||
                fr4End >= 0 & fr4Start > fr4End) {
            throw new IllegalArgumentException("Bad markup: " + Arrays.toString(markup));
        }
    }

    private ReceptorRegion<S> getRegion(ReceptorRegionType receptorRegionType,
                                        S fullSequence, int start, int end) {
        if (start >= end || end == 0) {
            return new ReceptorRegion<>(receptorRegionType,
                    -1, -1,
                    fullSequence.getAlphabet().getEmptySequence(),
                    true);
        }

        boolean incomplete = false;

        if (start < 0) {
            start = 0;
            incomplete = true;
        }

        if (end < 0) {
            end = fullSequence.size();
            incomplete = true;
        }

        return new ReceptorRegion<>(receptorRegionType,
                start, end,
                fullSequence.getRange(start, end),
                incomplete);
    }

    private ReceptorRegion<S> getRegion(ReceptorRegionType receptorRegionType,
                                        S fullSequence) {
        int startIndex = receptorRegionType.getOrder();

        int start = markup[startIndex];

        if (start < 0) {
            // check next region only - truncation from beginning
            return getRegion(receptorRegionType, fullSequence, start, markup[startIndex + 1]);
        } else {
            // find end
            int end = -1;
            for (int i = startIndex + 1; i < 8; i++) {
                end = markup[i];
                if (end >= 0) {
                    break;
                }
            }
            return getRegion(receptorRegionType, fullSequence, start, end);
        }
    }

    public ReceptorRegion<S> getFr1Region(S fullSequence) {
        return getRegion(FR1, fullSequence);
    }

    public ReceptorRegion<S> getCdr1Region(S fullSequence) {
        return getRegion(CDR1, fullSequence);
    }

    public ReceptorRegion<S> getFr2Region(S fullSequence) {
        return getRegion(FR2, fullSequence);
    }

    public ReceptorRegion<S> getCdr2Region(S fullSequence) {
        return getRegion(CDR2, fullSequence);
    }

    public ReceptorRegion<S> getFr3Region(S fullSequence) {
        return getRegion(FR3, fullSequence);
    }

    public ReceptorRegion<S> getCdr3Region(S fullSequence) {
        return getRegion(CDR3, fullSequence);
    }

    public ReceptorRegion<S> getFr4Region(S fullSequence) {
        return getRegion(FR4, fullSequence);
    }

    public int getFr1Start() {
        return markup[FR1.getOrder()];
    }

    public int getCdr1Start() {
        return markup[CDR1.getOrder()];
    }

    public int getFr2Start() {
        return markup[FR2.getOrder()];
    }

    public int getCdr2Start() {
        return markup[CDR2.getOrder()];
    }

    public int getFr3Start() {
        return markup[FR3.getOrder()];
    }

    public int getCdr3Start() {
        return markup[CDR3.getOrder()];
    }

    public int getFr4Start() {
        return markup[FR4.getOrder()];
    }

    public int getFr4End() {
        return markup[FR4.getOrder() + 1];
    }

    public int getMarkupStart() {
        return markup[FR1.getOrder()];
    }

    public int getMarkupEnd() {
        return markup[FR4.getOrder() + 1];
    }
}
