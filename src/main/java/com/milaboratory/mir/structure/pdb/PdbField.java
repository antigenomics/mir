package com.milaboratory.mir.structure.pdb;

import java.util.Arrays;
import java.util.Objects;

public class PdbField {
    private final String value;

    public PdbField(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PdbField pdbField = (PdbField) o;
        return Objects.equals(value, pdbField.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
