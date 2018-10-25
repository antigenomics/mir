package com.milaboratory.mir.mhc;

public enum MhcChainType {
    Alpha(0), Beta(1);

    private final int order;

    MhcChainType(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public boolean matches(String alias) {
        return name().toLowerCase().equals(alias.toLowerCase());
    }
}
