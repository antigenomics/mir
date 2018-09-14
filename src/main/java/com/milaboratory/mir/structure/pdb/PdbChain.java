package com.milaboratory.mir.structure.pdb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PdbChain implements Comparable<PdbChain> {
    private final char chainName;
    private final List<Atom> atoms;

    public PdbChain(char chainName, List<Atom> atoms) {
        this(chainName, atoms, false);
    }

    PdbChain(char chainName, List<Atom> atoms, boolean unsafe) {
        this.chainName = chainName;
        this.atoms = Collections.unmodifiableList(unsafe ? atoms : new ArrayList<>(atoms));
    }

    public char getChainName() {
        return chainName;
    }

    public List<Atom> getAtoms() {
        return atoms;
    }

    @Override
    public String toString() {
        return chainName + ":\n" +
                atoms.stream().limit(10).map(Atom::toString).collect(Collectors.joining("\n")) +
                "\n...";
    }

    @Override
    public int compareTo(PdbChain o) {
        return Character.compare(chainName, o.chainName);
    }
}
