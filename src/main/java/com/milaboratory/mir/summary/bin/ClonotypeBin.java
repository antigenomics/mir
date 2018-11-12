package com.milaboratory.mir.summary.bin;

public class ClonotypeBin {
    private final ClonotypeKey clonotypeKey;
    private final float weight;

    public ClonotypeBin(ClonotypeKey clonotypeKey, float weight) {
        this.clonotypeKey = clonotypeKey;
        this.weight = weight;
    }

    public ClonotypeKey getClonotypeKey() {
        return clonotypeKey;
    }

    public float getWeight() {
        return weight;
    }
}
