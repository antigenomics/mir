package com.antigenomics.mir.graph;

import com.antigenomics.mir.segment.Segment;
import com.milaboratory.core.alignment.*;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.Sequence;

import java.util.concurrent.ConcurrentHashMap;

public final class CachedSegmentAligner<S extends Sequence<S>> extends BasicAligner<Segment, S> {
    private final ConcurrentHashMap<String, Alignment<S>> alignmentCache = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public CachedSegmentAligner(AlignmentScoring<S> alignmentScoring) {
        super(alignmentScoring,
                alignmentScoring.getAlphabet() == AminoAcidSequence.ALPHABET ?
                        s -> (S) s.getGermlineSequenceAa() : s -> (S) s.getGermlineSequenceNt(),
                true);
    }

    private static String getKey(Segment segm1, Segment segm2) {
        return segm1.getId().compareTo(segm2.getId()) >= 0 ? segm1.getId() + " " + segm2.getId() : getKey(segm2, segm1);
    }

    @Override
    public Alignment<S> apply(final Segment segm1, final Segment segm2) {
        String key = getKey(segm1, segm2);
        return alignmentCache.computeIfAbsent(key, s -> super.apply(segm1, segm2));
    }
}
