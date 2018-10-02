package com.milaboratory.mir.graph;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.clonotype.ClonotypeCall;
import com.milaboratory.mir.clonotype.io.ClonotypeTablePipe;
import com.milaboratory.mir.mappers.stm.DummyExplicitAlignmentScoring;
import com.milaboratory.mir.mappers.stm.SequenceSearchScope;
import com.milaboratory.mir.mappers.stm.StmMapper;
import com.milaboratory.mir.pipe.BufferedPipe;
import com.milaboratory.mir.pipe.Pipe;
import com.milaboratory.mir.pipe.PipeFactory;

import java.util.function.Function;
import java.util.stream.Stream;

public class ClonotypeCdr3NtGraph implements Pipe<EdgeWithAlignment<NucleotideSequence>> {
    private final StmMapper<ClonotypeCall<Clonotype>, NucleotideSequence> stmMapper;

    public ClonotypeCdr3NtGraph(ClonotypeTablePipe<Clonotype> clonotypes,
                                SequenceSearchScope searchScope) {
        this.stmMapper = new StmMapper<>(
                clonotypes.iterable(),
                x -> x.getClonotype().getCdr3Nt(),
                NucleotideSequence.ALPHABET,
                searchScope,
                DummyExplicitAlignmentScoring.instance()
        );
    }

    @Override
    public Stream<EdgeWithAlignment<NucleotideSequence>> stream() {
        return stmMapper.stream().flatMap(new Function<ClonotypeCall<Clonotype>, Stream<? extends EdgeWithAlignment<NucleotideSequence>>>() {
            @Override
            public Stream<? extends EdgeWithAlignment<NucleotideSequence>> apply(ClonotypeCall<Clonotype> clonotypeClonotypeCall) {
                return null;
            }
        });
    }

    @Override
    public Stream<EdgeWithAlignment<NucleotideSequence>> parallelStream() {
        return stream().parallel();
    }
}
