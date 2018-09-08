package com.milaboratory.mir.clonotype.parser;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.segment.*;

public abstract class AbstractClonotypeTableParser<T extends Clonotype> implements ClonotypeTableParser<T> {
    private final String[] header;
    private final SegmentLibrary segmentLibrary;
    private final boolean majorAlleles;

    public AbstractClonotypeTableParser(String[] header,
                                        SegmentLibrary segmentLibrary,
                                        boolean majorAlleles) {
        this.header = header;
        this.segmentLibrary = segmentLibrary;
        this.majorAlleles = majorAlleles;
    }

    public String[] getHeader() {
        return header.clone();
    }

    public SegmentLibrary getSegmentLibrary() {
        return segmentLibrary;
    }

    public boolean isMajorAlleles() {
        return majorAlleles;
    }

    protected SegmentCall<VariableSegment> getV(String id, float score) {
        return new SegmentCall<>(majorAlleles ? segmentLibrary.getVMajor(id) : segmentLibrary.getV(id), score);
    }

    protected SegmentCall<DiversitySegment> getD(String id, float score) {
        return new SegmentCall<>(majorAlleles ? segmentLibrary.getDMajor(id) : segmentLibrary.getD(id), score);
    }

    protected SegmentCall<JoiningSegment> getJ(String id, float score) {
        return new SegmentCall<>(majorAlleles ? segmentLibrary.getJMajor(id) : segmentLibrary.getJ(id), score);
    }

    protected SegmentCall<ConstantSegment> getC(String id, float score) {
        return new SegmentCall<>(majorAlleles ? segmentLibrary.getCMajor(id) : segmentLibrary.getC(id), score);
    }
}
