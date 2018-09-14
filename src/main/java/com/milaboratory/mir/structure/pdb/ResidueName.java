package com.milaboratory.mir.structure.pdb;

public class ResidueName extends PdbField {
    public ResidueName(String value) {
        super(value);
        if (value.length() != 3) {
            throw new IllegalArgumentException("Should be 3 charecters long");
        }
    }
}
