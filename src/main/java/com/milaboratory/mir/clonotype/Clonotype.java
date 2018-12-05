package com.milaboratory.mir.clonotype;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.segment.*;

import java.util.List;

public interface Clonotype {
    NucleotideSequence getCdr3Nt();

    AminoAcidSequence getCdr3Aa();

    List<SegmentCall<VariableSegment>> getVariableSegmentCalls();

    List<SegmentCall<DiversitySegment>> getDiversitySegmentCalls();

    List<SegmentCall<JoiningSegment>> getJoiningSegmentCalls();

    List<SegmentCall<ConstantSegment>> getConstantSegmentCalls();

    default double getWeight() {
        return 1.0;
    }
}
