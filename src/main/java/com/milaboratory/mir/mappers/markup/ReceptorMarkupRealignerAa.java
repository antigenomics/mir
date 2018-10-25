package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.SequenceMapperFactory;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.SegmentLibrary;
import com.milaboratory.mir.segment.VariableSegment;

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
