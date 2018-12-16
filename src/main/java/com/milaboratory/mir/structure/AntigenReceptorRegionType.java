package com.milaboratory.mir.structure;

public enum AntigenReceptorRegionType {
    FR1(false), CDR1(true),
    FR2(false), CDR2(true),
    FR3(false), CDR3(true),
    FR4(false);

    private final boolean isCdr;

    AntigenReceptorRegionType(boolean isCdr) {
        this.isCdr = isCdr;
    }

    public boolean isCdr() {
        return isCdr;
    }
}
