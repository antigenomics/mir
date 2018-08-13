package com.milaboratory.mir.clonotype;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.segment.SegmentId;

public interface Clonotype {
    NucleotideSequence getCdr3Nt();

    AminoAcidSequence getCdr3Aa();

    SegmentId getV();

    SegmentId getD();

    SegmentId getJ();
}
