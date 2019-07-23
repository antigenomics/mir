package com.antigenomics.mir.graph;

import com.antigenomics.mir.clonotype.Clonotype;
import com.milaboratory.core.alignment.AlignmentScoring;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.Sequence;


public final class Cdr3Aligner<S extends Sequence<S>> extends BasicAligner<Clonotype, S> {
    @SuppressWarnings("unchecked")
    public Cdr3Aligner(AlignmentScoring<S> alignmentScoring) {
        super(alignmentScoring, alignmentScoring.getAlphabet() == AminoAcidSequence.ALPHABET ?
                c -> (S) c.getCdr3Aa() : c -> (S) c.getCdr3Nt(), false);
    }
}
