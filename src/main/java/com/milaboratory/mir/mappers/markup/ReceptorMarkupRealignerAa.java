package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.SequenceMapperFactory;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.SegmentLibrary;
import com.milaboratory.mir.segment.VariableSegment;

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
        this(segmentLibrary, mapperFactory, majorAllelesOnly, true);
    }

    public ReceptorMarkupRealignerAa(SegmentLibrary segmentLibrary,
                                     SequenceMapperFactory<AminoAcidSequence> mapperFactory,
                                     boolean majorAllelesOnly,
                                     boolean requireReferencePointPresence) {
        super(new SegmentMarkupRealignerAa<>(
                        majorAllelesOnly ? segmentLibrary.getAllVMajor() : segmentLibrary.getAllV(), mapperFactory,
                        requireReferencePointPresence
                ),
                new SegmentMarkupRealignerAa<>(
                        majorAllelesOnly ? segmentLibrary.getAllJMajor() : segmentLibrary.getAllJ(), mapperFactory,
                        requireReferencePointPresence
                ));
    }
}
