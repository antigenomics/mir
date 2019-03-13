package com.antigenomics.mir.mappers.markup;

import com.antigenomics.mir.segment.JoiningSegment;
import com.antigenomics.mir.segment.SegmentLibrary;
import com.antigenomics.mir.segment.VariableSegment;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.antigenomics.mir.mappers.SequenceMapperFactory;

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
