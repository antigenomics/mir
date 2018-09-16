package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.mappers.SequenceMapperFactory;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.SegmentLibrary;
import com.milaboratory.mir.segment.VariableSegment;

public final class ReceptorMarkupRealignerNt extends ReceptorMarkupRealigner<NucleotideSequence> {
    public ReceptorMarkupRealignerNt(SegmentMarkupRealignerNt<VariableSegment> variableSegmentRealigner,
                                     SegmentMarkupRealignerNt<JoiningSegment> joiningSegmentRealigner) {
        super(variableSegmentRealigner, joiningSegmentRealigner);
    }

    public ReceptorMarkupRealignerNt(SegmentLibrary segmentLibrary,
                                     SequenceMapperFactory<NucleotideSequence> mapperFactory) {
        this(segmentLibrary, mapperFactory, false);
    }

    public ReceptorMarkupRealignerNt(SegmentLibrary segmentLibrary,
                                     SequenceMapperFactory<NucleotideSequence> mapperFactory,
                                     boolean majorAllelesOnly) {
        this(segmentLibrary, mapperFactory, majorAllelesOnly, true);
    }

    public ReceptorMarkupRealignerNt(SegmentLibrary segmentLibrary,
                                     SequenceMapperFactory<NucleotideSequence> mapperFactory,
                                     boolean majorAllelesOnly,
                                     boolean requireReferencePointPresence) {
        super(new SegmentMarkupRealignerNt<>(
                        majorAllelesOnly ? segmentLibrary.getAllVMajor() : segmentLibrary.getAllV(), mapperFactory,
                        requireReferencePointPresence
                ),
                new SegmentMarkupRealignerNt<>(
                        majorAllelesOnly ? segmentLibrary.getAllJMajor() : segmentLibrary.getAllJ(), mapperFactory,
                        requireReferencePointPresence
                ));
    }
}
