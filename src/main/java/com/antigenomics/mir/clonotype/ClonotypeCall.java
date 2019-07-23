package com.antigenomics.mir.clonotype;

import com.antigenomics.mir.segment.*;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import java.util.List;
import java.util.Objects;
import java.util.Map;

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
    public Map<String, String> getAnnotations() {
        return clonotype.getAnnotations();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClonotypeCall<?> that = (ClonotypeCall<?>) o;
        return id == that.id &&
                count == that.count &&
                Double.compare(that.frequency, frequency) == 0 &&
                clonotype.equals(that.clonotype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, count, frequency, clonotype);
    }
}
