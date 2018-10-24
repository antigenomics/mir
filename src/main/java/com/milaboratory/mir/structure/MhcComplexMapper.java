package com.milaboratory.mir.structure;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.SequenceMapperFactory;
import com.milaboratory.mir.mappers.markup.MarkupRealignmentResult;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.mhc.MhcAllele;
import com.milaboratory.mir.mappers.markup.MhcMarkupRealigner;
import com.milaboratory.mir.mhc.MhcRegionType;
import com.milaboratory.mir.structure.pdb.Chain;

import java.util.*;

public class MhcComplexMapper {
    private final MhcMarkupRealigner mhcMarkupRealigner;

    public MhcComplexMapper(Collection<MhcAllele> alleles,
                            SequenceMapperFactory<AminoAcidSequence> mapperFactory) {
        this.mhcMarkupRealigner = new MhcMarkupRealigner(alleles, mapperFactory);
    }

    private Optional<MhcComplex> map(List<Chain> chains) {
        var resList = new ArrayList<ResultTuple>();
        for (Chain c : chains) {
            mhcMarkupRealigner
                    .recomputeMarkup(c.getSequence())
                    .ifPresent(x -> resList.add(new ResultTuple(x, c)));
        }
        resList.sort(ResultTuple::compareTo);
        if (resList.size() < 2) {
            return Optional.empty();
        } else {
            return Optional.of(
                    new MhcComplex(
                            createChain(resList.get(0)),
                            createChain(resList.get(1))
                    )
            );
        }
    }

    private static MhcChain createChain(ResultTuple resultTuple) {
        return new MhcChain((MhcAllele) resultTuple.result.getPayload(),
                resultTuple.result.getMarkup(),
                resultTuple.chain);
    }

    private static class ResultTuple implements Comparable<ResultTuple> {
        final MarkupRealignmentResult<AminoAcidSequence, MhcRegionType, ? extends SequenceRegionMarkup> result;
        final Chain chain;

        public ResultTuple(MarkupRealignmentResult<AminoAcidSequence, MhcRegionType, ? extends SequenceRegionMarkup> result, Chain chain) {
            this.result = result;
            this.chain = chain;
        }

        @Override
        public int compareTo(ResultTuple o) {
            return -Integer.compare(result.getNumberOfMatches(), o.result.getNumberOfMatches());
        }
    }
}
