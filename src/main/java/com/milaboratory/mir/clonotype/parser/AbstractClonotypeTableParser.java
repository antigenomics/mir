package com.milaboratory.mir.clonotype.parser;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.segment.SegmentLibrary;

public abstract class AbstractClonotypeTableParser<T extends Clonotype> implements ClonotypeTableParser<T> {
    protected final String[] header;
    protected final SegmentLibrary segmentLibrary;

    public AbstractClonotypeTableParser(String[] header, SegmentLibrary segmentLibrary) {
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
