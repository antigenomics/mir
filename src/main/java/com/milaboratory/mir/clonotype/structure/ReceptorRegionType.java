package com.milaboratory.mir.clonotype.structure;

public enum ReceptorRegionType {
    FR1(0), CDR1(1), FR2(2), CDR2(3), FR3(4), CDR3(5), FR4(6);

    private final int order;

    ReceptorRegionType(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
