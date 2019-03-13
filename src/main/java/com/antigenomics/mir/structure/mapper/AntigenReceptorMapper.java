package com.antigenomics.mir.structure.mapper;

import com.antigenomics.mir.mappers.markup.MarkupRealigner;
import com.antigenomics.mir.mappers.markup.ReceptorMarkupRealigner;
import com.antigenomics.mir.mappers.markup.ReceptorMarkupRealignerAa;
import com.antigenomics.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.mappers.SequenceMapperFactory;
import com.antigenomics.mir.mappers.markup.*;
import com.antigenomics.mir.segment.JoiningSegment;
import com.antigenomics.mir.segment.MissingConstantSegment;
import com.antigenomics.mir.segment.VariableSegment;
import com.antigenomics.mir.structure.AntigenReceptor;
import com.antigenomics.mir.structure.AntigenReceptorChain;
import com.antigenomics.mir.structure.AntigenReceptorRegionType;

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
        throw new UnsupportedOperationException("Cannot create dummy chain for TCR");
    }
}