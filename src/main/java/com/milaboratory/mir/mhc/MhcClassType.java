package com.milaboratory.mir.mhc;

public enum MhcClassType {
    MHCI, MHCII;

    public boolean matches(String alias) {
        return name().toLowerCase().equals(alias.toLowerCase());
    }
}
