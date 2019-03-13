package com.antigenomics.mir.segment;

public enum Gene {
    TRA("TRA", false, 0), TRB("TRB", true, 1),
    TRG("TRG", false, 0), TRD("TRD", true, 1),
    IGK("IGK", false, 0), IGL("IGL", false, 0), IGH("IGH", true, 1);

    private final String code;
    private final boolean hasD;
    private final int order;

    Gene(String code, boolean hasD, int order) {
        this.code = code;
        this.hasD = hasD;
        this.order = order;
    }

    public String getCode() {
        return code;
    }

    public boolean hasD() {
        return hasD;
    }

    public int getOrder() {
        return order;
    }

    public boolean matches(String name) {
        return code.equalsIgnoreCase(name);
    }
}
