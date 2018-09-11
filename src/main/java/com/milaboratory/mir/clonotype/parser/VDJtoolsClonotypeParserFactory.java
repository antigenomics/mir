package com.milaboratory.mir.clonotype.parser;


import com.milaboratory.mir.clonotype.rearrangement.ReadlessClonotypeImpl;
import com.milaboratory.mir.segment.SegmentLibrary;

public class VDJtoolsClonotypeParserFactory implements ClonotypeTableParserFactory<ReadlessClonotypeImpl> {
    private final SegmentLibrary segmentLibrary;
    private final boolean majorAlleles;

    public VDJtoolsClonotypeParserFactory(SegmentLibrary segmentLibrary, boolean majorAlleles) {
        this.segmentLibrary = segmentLibrary;
        this.majorAlleles = majorAlleles;
    }

    @Override
    public VDJtoolsClonotypeParser create(String[] header) {
        return new VDJtoolsClonotypeParser(header, segmentLibrary, majorAlleles);
    }
}
