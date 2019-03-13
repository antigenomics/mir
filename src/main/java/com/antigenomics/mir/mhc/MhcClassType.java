package com.antigenomics.mir.mhc;

public enum MhcClassType {
    MHCI, MHCII, CHIMERIC;

    public boolean matches(String alias) {
        return name().toLowerCase().equals(alias.toLowerCase());
    }

    public static MhcClassType combine(MhcClassType a, MhcClassType b) {
        return a == b ? a : CHIMERIC;
    }
}
