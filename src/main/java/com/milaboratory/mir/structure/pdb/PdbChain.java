package com.milaboratory.mir.structure.pdb;

import com.milaboratory.core.Range;
import com.milaboratory.core.sequence.AminoAcidAlphabet;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.SequenceRegion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PdbChain implements Comparable<PdbChain> {
    private final char chainName;
    private final List<Atom> atoms;
    private final AminoAcidSequence sequence;
    private final Range pdbNumberingRange;

    public PdbChain(char chainName, List<Atom> atoms) {
        this(chainName, atoms, false);
    }

    PdbChain(char chainName, List<Atom> atoms, boolean unsafe) {
        this.chainName = chainName;
        if (atoms.isEmpty()) {
            throw new IllegalArgumentException("No atoms provided for chain");
        }
        this.atoms = Collections.unmodifiableList(unsafe ? atoms : new ArrayList<>(atoms));
        int minResidue = Integer.MAX_VALUE, maxResidue = Integer.MIN_VALUE;
        for (Atom atom : atoms) {
            short residueSequenceNumber = atom.getResidueSequenceNumber();
            minResidue = Math.min(minResidue, residueSequenceNumber);
            maxResidue = Math.max(maxResidue, residueSequenceNumber);
        }
        this.pdbNumberingRange = new Range(minResidue, maxResidue + 1);
        byte[] aas = new byte[pdbNumberingRange.length()];
        Arrays.fill(aas, AminoAcidAlphabet.X);
        for (Atom atom : atoms) {
            short residueSequenceNumber = atom.getResidueSequenceNumber();
            byte code = atom.getResidueName().getCode();
            aas[residueSequenceNumber - minResidue] = code;
        }
        this.sequence = new AminoAcidSequence(aas);
    }

    public <E extends Enum<E>> PdbChainRegion<E> getRegion(SequenceRegion<AminoAcidSequence, E> region) {
        var chainRegion = new PdbChainRegion<>(
                atoms.stream().filter(x -> {
                    int pos = x.getResidueSequenceNumber() - pdbNumberingRange.getFrom();
                    return region.contains(pos);
                }).collect(Collectors.toList()), this, region, true);
        if (!region.getSequence().equals(chainRegion.getSequence())) {
            throw new IllegalArgumentException("Range sequence is '" + region.getSequence() +
                    "' while sequence from PDB is '" + chainRegion.getSequence() + "'");
        }
        return chainRegion;
    }

    public char getChainName() {
        return chainName;
    }

    public Range getPdbNumberingRange() {
        return pdbNumberingRange;
    }

    public List<Atom> getAtoms() {
        return atoms;
    }

    @Override
    public String toString() {
        return chainName + ":\n" +
                sequence + " " + pdbNumberingRange + "\n" +
                atoms.stream().limit(10).map(Atom::toString).collect(Collectors.joining("\n")) +
                (atoms.size() > 10 ? "\n..." : "");
    }

    @Override
    public int compareTo(PdbChain o) {
        return Character.compare(chainName, o.chainName);
    }

    public AminoAcidSequence getSequence() {
        return sequence;
    }
}
