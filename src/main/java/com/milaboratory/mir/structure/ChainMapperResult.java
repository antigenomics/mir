package com.milaboratory.mir.structure;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.MarkupRealignmentResult;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.structure.pdb.Chain;

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
