package com.milaboratory.mir.structure.pdb;

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
