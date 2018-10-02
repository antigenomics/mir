package com.milaboratory.mir.clonotype.parser;


import com.milaboratory.mir.clonotype.rearrangement.ReadlessClonotypeImpl;
import com.milaboratory.mir.segment.SegmentLibrary;

public class MixcrClonotypeParserFactory implements ClonotypeTableParserFactory<ReadlessClonotypeImpl> {
    private final SegmentLibrary segmentLibrary;
    private final boolean majorAlleles;

    public MixcrClonotypeParserFactory(SegmentLibrary segmentLibrary, boolean majorAlleles) {
        this.segmentLibrary = segmentLibrary;
        this.majorAlleles = majorAlleles;
    }

    @Override
    public MixcrClonotypeParser create(String[] header) {
        return new MixcrClonotypeParser(header, segmentLibrary, majorAlleles);
    }
}
