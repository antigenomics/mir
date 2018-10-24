package com.milaboratory.mir.mhc;

public enum MhcChainType {
    Alpha, Beta;

    public boolean matches(String alias) {
        return name().toLowerCase().equals(alias.toLowerCase());
    }
}
