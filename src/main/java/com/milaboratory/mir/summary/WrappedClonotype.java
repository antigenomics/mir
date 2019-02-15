package com.milaboratory.mir.summary;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.segment.*;
import com.milaboratory.mir.summary.binning.DummyGroup;

import java.util.List;

public final class WrappedClonotype<T extends Clonotype, G extends ClonotypeGroup>
        implements Clonotype {
    private final G group;
    private final double weight;
    private final T clonotype;

    public static <T extends Clonotype> WrappedClonotype<T, DummyGroup> dummy(T clonotype) {
        return new WrappedClonotype<>(DummyGroup.INSTANCE, 1.0, clonotype);
    }

    // todo: move weight last param
    public WrappedClonotype(G group, double weight, T clonotype) {
        this.group = group;
        this.weight = weight;
        this.clonotype = clonotype;
    }

    public G getGroup() {
        return group;
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

    public double getWeight() {
        return weight;
    }

    public T getClonotype() {
        return clonotype;
    }

    public WrappedClonotype<T, G> scaleWeight(double scalar) {
        return new WrappedClonotype<>(group, weight * scalar, clonotype);
    }

    public WrappedClonotype<T, G> scaleWeight() {
        return scaleWeight(this.clonotype.getWeight());
    }
}
