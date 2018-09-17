package com.milaboratory.mir.structure.pdb.parser;

public enum AtomType {
    ATOM("ATOM  "), TER("TER   ");

    private final String id;

    AtomType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
