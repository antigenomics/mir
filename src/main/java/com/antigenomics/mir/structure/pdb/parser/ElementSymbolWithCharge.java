package com.antigenomics.mir.structure.pdb.parser;

public class ElementSymbolWithCharge extends PdbField {
    public ElementSymbolWithCharge(String value) {
        super(value);
        if (value.length() != 4) {
            throw new IllegalArgumentException("Should be 4 charecters long");
        }
    }
}
