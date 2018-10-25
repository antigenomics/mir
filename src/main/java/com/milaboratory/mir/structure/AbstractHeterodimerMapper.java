package com.milaboratory.mir.structure;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.MarkupRealigner;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.structure.pdb.Chain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public abstract class AbstractHeterodimerMapper<E extends Enum<E>, C extends StructureChainWithMarkup<E>,
        O extends HeterodimerComplex<E, C>> {
    private final MarkupRealigner<AminoAcidSequence, E, ? extends SequenceRegionMarkup> mhcMarkupRealigner;
    private final HeterodimerComplexFactory<E, C, O> factory;

    public AbstractHeterodimerMapper(MarkupRealigner<AminoAcidSequence, E, ? extends SequenceRegionMarkup>
                                             mhcMarkupRealigner,
                                     HeterodimerComplexFactory<E, C, O> factory) {
        this.mhcMarkupRealigner = mhcMarkupRealigner;
        this.factory = factory;
    }

    public Optional<O> map(Collection<Chain> chains) {
        var resList = new ArrayList<ChainMapperResult<E>>();
        for (Chain c : chains) {
            mhcMarkupRealigner
                    .recomputeMarkup(c.getSequence())
                    .ifPresent(x -> resList.add(new ChainMapperResult<>(x, c)));
        }
        resList.sort(ChainMapperResult::compareTo);
        if (resList.size() < 2) {
            return Optional.empty();
        } else {
            return Optional.of(
                    factory.create(
                            createChain(resList.get(0)),
                            createChain(resList.get(1))
                    )
            );
        }
    }

    protected abstract C createChain(ChainMapperResult<E> result);
}
