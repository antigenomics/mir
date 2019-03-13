package com.antigenomics.mir.clonotype.parser;


import com.antigenomics.mir.clonotype.rearrangement.ReadlessClonotypeImpl;
import com.antigenomics.mir.segment.SegmentLibrary;

public class VDJtoolsClonotypeParserFactory implements ClonotypeTableParserFactory<ReadlessClonotypeImpl> {
    private final SegmentLibrary segmentLibrary;
    private final boolean majorAlleles;

    public VDJtoolsClonotypeParserFactory(SegmentLibrary segmentLibrary, boolean majorAlleles) {
        this.segmentLibrary = segmentLibrary;
        this.majorAlleles = majorAlleles;
    }

    public VDJtoolsClonotypeParserFactory(SegmentLibrary segmentLibrary) {
        this(segmentLibrary, true);
    }

    @Override
    public VDJdbClonotypeParser create(String[] header) {
        return new VDJdbClonotypeParser(header, segmentLibrary, majorAlleles);
    }
}
