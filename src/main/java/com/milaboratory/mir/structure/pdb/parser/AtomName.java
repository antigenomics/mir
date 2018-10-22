package com.milaboratory.mir.structure.pdb.parser;

public class AtomName extends PdbField {
    public AtomName(String value) {
        super(value);
        if (value.length() != 4) {
            throw new IllegalArgumentException("Should be 4 characters long");
        }
    }
}
