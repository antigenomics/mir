package com.antigenomics.mir.structure.mapper;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.mappers.markup.MarkupRealignmentResult;
import com.antigenomics.mir.mappers.markup.SequenceRegionMarkup;
import com.antigenomics.mir.structure.pdb.Chain;

class ChainMapperResult<E extends Enum<E>> implements Comparable<ChainMapperResult<E>> {
    private final MarkupRealignmentResult<AminoAcidSequence, E, ? extends SequenceRegionMarkup> result;
    private final Chain chain;

    public ChainMapperResult(MarkupRealignmentResult<AminoAcidSequence, E, ? extends SequenceRegionMarkup> result,
                             Chain chain) {
        this.result = result;
        this.chain = chain;
    }

    public MarkupRealignmentResult<AminoAcidSequence, E, ? extends SequenceRegionMarkup> getResult() {
        return result;
    }

    public Chain getChain() {
        return chain;
    }

    @Override
    public int compareTo(ChainMapperResult<E> o) {
        return -Integer.compare(result.getNumberOfMatches(), o.result.getNumberOfMatches());
    }
}
