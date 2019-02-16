package com.milaboratory.mir.clonotype;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.segment.*;

import java.util.List;

public class ClonotypeCall<T extends Clonotype> implements Clonotype,
        Comparable<ClonotypeCall<T>> {
    public static <T extends Clonotype> ClonotypeCall<T> getDummy() {
        return new ClonotypeCall<>(-1, -1, -1, null);
    }

    private final int id;
    private final long count;
    private final double frequency;
    private final T clonotype;

    public ClonotypeCall(int id, long count, double frequency, T clonotype) {
        this.id = id;
        this.count = count;
        this.frequency = frequency;
        this.clonotype = clonotype;
    }

    public int getId() {
        return id;
    }

    public long getCount() {
        return count;
    }

    public double getFrequency() {
        return frequency;
    }

    public T getClonotype() {
        return clonotype;
    }

    @Override
    public NucleotideSequence getCdr3Nt() {
        return clonotype.getCdr3Nt();
    }

    @Override
    public AminoAcidSequence getCdr3Aa() {
        return clonotype.getCdr3Aa();
    }

    @Override
    public List<SegmentCall<VariableSegment>> getVariableSegmentCalls() {
        return clonotype.getVariableSegmentCalls();
    }

    @Override
    public List<SegmentCall<DiversitySegment>> getDiversitySegmentCalls() {
        return clonotype.getDiversitySegmentCalls();
    }

    @Override
    public List<SegmentCall<JoiningSegment>> getJoiningSegmentCalls() {
        return clonotype.getJoiningSegmentCalls();
    }

    @Override
    public List<SegmentCall<ConstantSegment>> getConstantSegmentCalls() {
        return clonotype.getConstantSegmentCalls();
    }

    @Override
    public double getWeight() {
        return frequency;
    }

    @Override
    public String toString() {
        return id + "\t" + count + "\t" + frequency + "\t" + clonotype;
    }

    @Override
    public int compareTo(ClonotypeCall<T> o) {
        return Long.compare(count, o.count);
    }
}
