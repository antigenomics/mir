package com.antigenomics.mir.rearrangement;

import com.antigenomics.mir.pipe.Generator;

public interface RearrangementModel<T extends RearrangementModel<T>> extends Generator<RearrangementTemplate> {
    boolean hasD();

    default double computeProbability(RearrangementTemplate rearrangementTemplate) {
        throw new UnsupportedOperationException(); // todo
    }

    default void update(RearrangementTemplate rearrangementTemplate, double weight) {
        throw new UnsupportedOperationException(); // todo
    }

    default void update(RearrangementTemplate rearrangementTemplate) {
        update(rearrangementTemplate, computeProbability(rearrangementTemplate));
    }

    T getUpdatedModel();

    T getModelTemplate();
}
