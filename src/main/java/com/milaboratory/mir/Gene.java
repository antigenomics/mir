package com.milaboratory.mir;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Gene {
    TRA("tra", false), TRB("trb", true),
    TRG("trg", false), TRD("trd", true),
    IGK("igk", false), IGL("igl", false), IGH("igh", true);

    private final String code;
    private final boolean hasD;
    private final Set<String> allowedValues;

    Gene(String code, boolean hasD) {
        this.code = code;
        this.hasD = hasD;
        this.allowedValues = Arrays.stream(values()).map(Gene::getCode).collect(Collectors.toSet());
    }

    public String getCode() {
        return code;
    }

    public boolean hasD() {
        return hasD;
    }

    public boolean matches(String name) {
        return this.allowedValues.contains(name.toLowerCase());
    }
}
