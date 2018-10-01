package com.milaboratory.mir.search;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.clonotype.ClonotypeCall;
import com.milaboratory.mir.clonotype.io.ClonotypeTablePipe;
import com.milaboratory.mir.mappers.stm.ExplicitAlignmentScoring;
import com.milaboratory.mir.mappers.stm.SequenceSearchScope;
import com.milaboratory.mir.mappers.stm.StmMapper;

public class VJGroupedCdr3NtSearcher {
    private final StmMapper<ClonotypeCall<Clonotype>, NucleotideSequence> stmMapper;

    public VJGroupedCdr3NtSearcher(ClonotypeTablePipe<Clonotype> clonotypes,
                                   SequenceSearchScope searchScope,
                                   ExplicitAlignmentScoring<NucleotideSequence> scoring) {
        this.stmMapper = new StmMapper<>(
                clonotypes.iterable(),
                x -> x.getClonotype().getCdr3Nt(),
                NucleotideSequence.ALPHABET,
                searchScope, scoring
        );
    }
}
