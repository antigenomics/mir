package com.milaboratory.mir.mappers;

import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.sequence.Sequence;

public class HitWithAlignment<Q, T, S extends Sequence<S>> extends Hit<Q, T, S> {
    private final Alignment<S> alignment;


    public HitWithAlignment(Q query, T target,
                            Alignment<S> alignment) {
        super(query, target, alignment.getScore(), alignment.getAbsoluteMutations());
        this.alignment = alignment;
    }

    public Alignment<S> getAlignment() {
        return alignment;
    }
}
