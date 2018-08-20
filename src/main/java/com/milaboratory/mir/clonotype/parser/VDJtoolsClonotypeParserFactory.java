package com.milaboratory.mir.clonotype.parser;


import com.milaboratory.mir.clonotype.ReadlessClonotypeImpl;
import com.milaboratory.mir.segment.SegmentLibrary;

public class VDJtoolsClonotypeParserFactory implements ClonotypeTableParserFactory<ReadlessClonotypeImpl> {
    private final SegmentLibrary segmentLibrary;

    public VDJtoolsClonotypeParserFactory(SegmentLibrary segmentLibrary) {
        this.segmentLibrary = segmentLibrary;
    }

    @Override
    public VDJtoolsClonotypeParser create(String[] header) {
        return new VDJtoolsClonotypeParser(header, segmentLibrary);
    }
}
