package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.NucleotideSequence;

public interface SegmentSequenceLibrary {
    SegmentId getOrCreate(String name);

    NucleotideSequence getTrimmedVCdr3Part(SegmentId v, int trimmingSize);

    NucleotideSequence getTrimmedJCdr3Part(SegmentId j, int trimmingSize);

    NucleotideSequence getTrimmedDCdr3Part(SegmentId d, int trimmingSize5, int trimmingSize3);
}
