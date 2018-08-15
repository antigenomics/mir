package com.milaboratory.mir;

public enum Species {
    Human("hsa"), Mouse("mmu");

    private final String code;

    Species(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
