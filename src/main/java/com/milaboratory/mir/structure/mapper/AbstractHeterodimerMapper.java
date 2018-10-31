package com.milaboratory.mir.structure.mapper;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.MarkupRealigner;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.structure.HeterodimerComplex;
import com.milaboratory.mir.structure.HeterodimerComplexFactory;
import com.milaboratory.mir.structure.StructureChainWithMarkup;
import com.milaboratory.mir.structure.pdb.Chain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public abstract class AbstractHeterodimerMapper<E extends Enum<E>, C extends StructureChainWithMarkup<E>,
        O extends HeterodimerComplex<E, C>> {
    private final MarkupRealigner<AminoAcidSequence, E, ? extends SequenceRegionMarkup> markupRealigner;
    private final HeterodimerComplexFactory<E, C, O> factory;

    public AbstractHeterodimerMapper(MarkupRealigner<AminoAcidSequence, E, ? extends SequenceRegionMarkup>
                                             markupRealigner,
                                     HeterodimerComplexFactory<E, C, O> factory) {
        this.markupRealigner = markupRealigner;
        this.factory = factory;
    }

    public Optional<O> map(Collection<Chain> chains) {
        var resList = new ArrayList<ChainMapperResult<E>>();
        for (Chain c : chains) {
            markupRealigner
                    .recomputeMarkup(c.getSequence())
                    .ifPresent(x -> resList.add(new ChainMapperResult<>(x, c)));
        }
        resList.sort(ChainMapperResult::compareTo);
        if (resList.size() == 0) {
            return Optional.empty();
        } else if (resList.size() == 1) {
            return Optional.of(
                    factory.create(
                            createChain(resList.get(0)),
                            createDummy(resList.get(0))
                    )
            );
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

    protected abstract C createDummy(ChainMapperResult<E> template);
}
