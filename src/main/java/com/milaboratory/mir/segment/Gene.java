package com.milaboratory.mir.segment;

public enum Gene {
    TRA("TRA", false), TRB("TRB", true),
    TRG("TRG", false), TRD("TRD", true),
    IGK("IGK", false), IGL("IGL", false), IGH("IGH", true);

    private final String code;
    private final boolean hasD;

    Gene(String code, boolean hasD) {
        this.code = code;
        this.hasD = hasD;
    }

    public String getCode() {
        return code;
    }

    public boolean hasD() {
        return hasD;
    }

    public boolean matches(String name) {
        return code.equalsIgnoreCase(name);
    }
}
