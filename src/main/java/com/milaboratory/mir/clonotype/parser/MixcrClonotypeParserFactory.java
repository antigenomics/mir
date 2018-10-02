package com.milaboratory.mir.clonotype.parser;


import com.milaboratory.mir.clonotype.rearrangement.ReadlessClonotypeImpl;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.SegmentLibrary;
import com.milaboratory.mir.segment.Species;

public class MixcrClonotypeParserFactory implements ClonotypeTableParserFactory<ReadlessClonotypeImpl> {
    private final SegmentLibrary segmentLibrary;
    private final boolean majorAlleles, convertAlleles;

    public MixcrClonotypeParserFactory(SegmentLibrary segmentLibrary, boolean majorAlleles, boolean convertAlleles) {
        this.segmentLibrary = segmentLibrary;
        this.majorAlleles = majorAlleles;
        this.convertAlleles = convertAlleles;
    }

    public MixcrClonotypeParserFactory(SegmentLibrary segmentLibrary, boolean majorAlleles) {
        this(segmentLibrary, majorAlleles, true);
    }

    public MixcrClonotypeParserFactory(SegmentLibrary segmentLibrary) {
        this(segmentLibrary, true);
    }

    @Override
    public MixcrClonotypeParser create(String[] header) {
        return new MixcrClonotypeParser(header, segmentLibrary, majorAlleles, convertAlleles);
    }
}
