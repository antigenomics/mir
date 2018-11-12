package com.milaboratory.mir.structure.mapper;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.SequenceMapperFactory;
import com.milaboratory.mir.mappers.markup.*;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.MissingConstantSegment;
import com.milaboratory.mir.segment.VariableSegment;
import com.milaboratory.mir.structure.AntigenReceptor;
import com.milaboratory.mir.structure.AntigenReceptorChain;
import com.milaboratory.mir.structure.AntigenReceptorRegionType;

import java.util.Collection;

public class AntigenReceptorMapper extends AbstractHeterodimerMapper<AntigenReceptorRegionType,
        AntigenReceptorChain, AntigenReceptor> {

    public AntigenReceptorMapper(Collection<VariableSegment> variableSegments,
                                 Collection<JoiningSegment> joiningSegments,
                                 SequenceMapperFactory<AminoAcidSequence> mapperFactory) {
        super(new ReceptorMarkupRealignerAa(variableSegments, joiningSegments, mapperFactory), AntigenReceptor::new);
    }

    public AntigenReceptorMapper(MarkupRealigner<AminoAcidSequence, AntigenReceptorRegionType, ? extends SequenceRegionMarkup> mhcMarkupRealigner) {
        super(mhcMarkupRealigner, AntigenReceptor::new);
    }

    @Override
    protected AntigenReceptorChain createChain(ChainMapperResult<AntigenReceptorRegionType> result) {
        var mapping = result.getResult();
        var vjPair = (ReceptorMarkupRealigner.VariableJoiningPair) mapping.getPayload();

        return new AntigenReceptorChain(
                mapping.getMarkup(),
                vjPair.getVariableSegment(),
                vjPair.getJoiningSegment(),
                MissingConstantSegment.INSTANCE,
                result.getChain()
        );
    }

    @Override
    protected AntigenReceptorChain createDummy(ChainMapperResult<AntigenReceptorRegionType> template) {
        throw new UnsupportedOperationException("Cannot createBins dummy chain for TCR");
    }
}