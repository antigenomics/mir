package com.milaboratory.mir.clonotype.rearrangement;

public class JunctionMarkup {
    private final int vEnd, jStart, dStart, dEnd;

    public JunctionMarkup(int vEnd, int jStart,
                          int dStart, int dEnd) {
        this.vEnd = vEnd;
        this.jStart = jStart;
        this.dStart = dStart;
        this.dEnd = dEnd;
    }

    public JunctionMarkup(int vEnd, int jStart) {
        this(vEnd, jStart, -1, -1);
    }

    public int getVEnd() {
        return vEnd;
    }

    public int getJStart() {
        return jStart;
    }

    public int getDStart() {
        return dStart;
    }

    public int getDEnd() {
        return dEnd;
    }
}
