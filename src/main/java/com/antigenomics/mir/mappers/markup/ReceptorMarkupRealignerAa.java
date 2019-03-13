package com.antigenomics.mir.mappers.markup;

import com.antigenomics.mir.segment.JoiningSegment;
import com.antigenomics.mir.segment.SegmentLibrary;
import com.antigenomics.mir.segment.VariableSegment;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.mappers.SequenceMapperFactory;

import java.util.Collection;

public final class ReceptorMarkupRealignerAa extends ReceptorMarkupRealigner<AminoAcidSequence> {
    public ReceptorMarkupRealignerAa(SegmentMarkupRealignerAa<VariableSegment> variableSegmentRealigner,
                                     SegmentMarkupRealignerAa<JoiningSegment> joiningSegmentRealigner) {
        super(variableSegmentRealigner, joiningSegmentRealigner);
    }

    public ReceptorMarkupRealignerAa(SegmentLibrary segmentLibrary,
                                     SequenceMapperFactory<AminoAcidSequence> mapperFactory) {
        this(segmentLibrary, mapperFactory, false);
    }

    public ReceptorMarkupRealignerAa(SegmentLibrary segmentLibrary,
                                     SequenceMapperFactory<AminoAcidSequence> mapperFactory,
                                     boolean majorAllelesOnly) {
        super(new SegmentMarkupRealignerAa<>(
                        majorAllelesOnly ? segmentLibrary.getAllVMajor() : segmentLibrary.getAllV(), mapperFactory
                ),
                new SegmentMarkupRealignerAa<>(
                        majorAllelesOnly ? segmentLibrary.getAllJMajor() : segmentLibrary.getAllJ(), mapperFactory
                ));
    }

    public ReceptorMarkupRealignerAa(Collection<VariableSegment> variableSegments,
                                     Collection<JoiningSegment> joiningSegments,
                                     SequenceMapperFactory<AminoAcidSequence> mapperFactory) {
        super(new SegmentMarkupRealignerAa<>(
                        variableSegments, mapperFactory
                ),
                new SegmentMarkupRealignerAa<>(
                        joiningSegments, mapperFactory
                ));
    }
}
