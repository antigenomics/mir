package com.antigenomics.mir.clonotype.rearrangement;

public class JunctionMarkup {
    public static final JunctionMarkup DUMMY = new JunctionMarkup(-1, -1);

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

    public String asRow() {
        return vEnd + "\t" + dStart + "\t" + dEnd + "\t" + jStart;
    }

    @Override
    public String toString() {
        return asRow();
    }
}
