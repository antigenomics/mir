package com.milaboratory.mir.clonotype;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.segment.DiversitySegment;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

public interface Clonotype {
    NucleotideSequence getCdr3Nt();

    AminoAcidSequence getCdr3Aa();

    VariableSegment getV();

    DiversitySegment getD();

    JoiningSegment getJ();
}
