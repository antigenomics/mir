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
        super(new SegmentMarkupRealignerNt<>(
                        majorAllelesOnly ? segmentLibrary.getAllVMajor() : segmentLibrary.getAllV(), mapperFactory
                ),
                new SegmentMarkupRealignerNt<>(
                        majorAllelesOnly ? segmentLibrary.getAllJMajor() : segmentLibrary.getAllJ(), mapperFactory
                ));
    }
}
