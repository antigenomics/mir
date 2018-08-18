package com.milaboratory.mir.clonotype.parser;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.segment.SegmentLibrary;

public abstract class ClonotypeTableParser<T extends Clonotype> implements TabularParser<T> {
    protected final String[] header;
    protected final SegmentLibrary segmentLibrary;

    public ClonotypeTableParser(String[] header, SegmentLibrary segmentLibrary) {
        this.header = header;
        this.segmentLibrary = segmentLibrary;
    }

    public String[] getHeader() {
        return header.clone();
    }

    public SegmentLibrary getSegmentLibrary() {
        return segmentLibrary;
    }
}
