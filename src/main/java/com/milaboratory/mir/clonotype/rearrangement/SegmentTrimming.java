package com.milaboratory.mir.clonotype.rearrangement;

public class SegmentTrimming {
    private final int vTrimming, jTrimming, dTrimming5, dTrimming3;

    public SegmentTrimming(int vTrimming, int jTrimming, int dTrimming5, int dTrimming3) {
        this.vTrimming = vTrimming;
        this.jTrimming = jTrimming;
        this.dTrimming5 = dTrimming5;
        this.dTrimming3 = dTrimming3;
    }

    public SegmentTrimming(int vTrimming, int jTrimming) {
        this(vTrimming, jTrimming, -1, -1);
    }

    public int getVTrimming() {
        return vTrimming;
    }

    public int getJTrimming() {
        return jTrimming;
    }

    public int getDTrimming5() {
        return dTrimming5;
    }

    public int getDTrimming3() {
        return dTrimming3;
    }
}
