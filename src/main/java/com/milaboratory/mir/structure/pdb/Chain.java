package com.milaboratory.mir.structure.pdb;

import com.milaboratory.core.Range;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.SequenceRegion;
import com.milaboratory.mir.structure.pdb.geometry.CoordinateSet;
import com.milaboratory.mir.structure.pdb.geometry.CoordinateTransformation;
import com.milaboratory.mir.structure.pdb.parser.RawAtom;

import java.util.*;
import java.util.stream.Collectors;

public class Chain implements Comparable<Chain>, CoordinateSet<Chain>, Iterable<Residue> {
    private final char chainIdentifier;
    private final List<Residue> residues;
    private final AminoAcidSequence sequence;
    private final Range originalRange;

    public static final Chain DUMMY = new Chain('?', new HashMap<>(), false);

    Chain(char chainIdentifier, Map<Short, List<RawAtom>> residueMap) {
        this(chainIdentifier, residueMap, true);
    }

    Chain(char chainIdentifier, Map<Short, List<RawAtom>> residueMap, boolean disallowEmpty) {
        if (disallowEmpty && residueMap.isEmpty()) {
            throw new IllegalArgumentException("Empty residue map");
        }
        this.chainIdentifier = chainIdentifier;
        this.residues = new ArrayList<>(residueMap.size());
        for (List<RawAtom> rawAtoms : residueMap.values()) {
            residues.add(new Residue(this, rawAtoms));
        }
        this.sequence = getSequence(residues);
        this.originalRange = new Range(0, residueMap.size());
    }

    Chain(Chain toCopy, List<Residue> newResidues) {
        this(toCopy, newResidues, 0);
    }

    Chain(Chain toCopy, List<Residue> newResidues, int position) {
        this.chainIdentifier = toCopy.chainIdentifier;
        this.residues = newResidues;
        this.originalRange = getRange(newResidues, position);
        this.sequence = getSequence(newResidues);
    }

    Chain(Chain toCopy, char chainIdentifier) {
        this.chainIdentifier = chainIdentifier;
        this.residues = toCopy.residues;
        this.originalRange = toCopy.originalRange;
        this.sequence = toCopy.sequence;
    }

    private static Range getRange(Collection<Residue> residues, int position) {
        if (residues.isEmpty()) {
            return new Range(position, position); // here is why we need position
        }

        int minResidue = Integer.MAX_VALUE, maxResidue = Integer.MIN_VALUE;
        for (Residue residue : residues) {
            minResidue = Math.min(minResidue, residue.getSequentialResidueSequenceNumber());
            maxResidue = Math.max(maxResidue, residue.getSequentialResidueSequenceNumber());
        }
        return new Range(minResidue, maxResidue + 1);
    }

    public static AminoAcidSequence getSequence(List<Residue> residues) {
        byte[] aas = new byte[residues.size()];
        for (int i = 0; i < aas.length; i++) {
            aas[i] = residues.get(i).getResidueName().getCode();
        }
        return new AminoAcidSequence(aas);
    }

    public <E extends Enum<E>> ChainRegion<E> extractRegion(SequenceRegion<AminoAcidSequence, E> region) {
        return new ChainRegion<>(
                this,
                residues
                        .stream()
                        .filter(x -> region.contains(x.getSequentialResidueSequenceNumber()))
                        .collect(Collectors.toList()),
                region);
    }

    public char getChainIdentifier() {
        return chainIdentifier;
    }

    public Residue getResidue(int index) {
        return residues.get(index);
    }

    public Range getOriginalRange() {
        return originalRange;
    }

    public List<Residue> getResidues() {
        return Collections.unmodifiableList(residues);
    }

    @Override
    public Chain applyTransformation(CoordinateTransformation transformation) {
        return new Chain(this, residues.stream()
                .map(x -> x.applyTransformation(transformation)).collect(Collectors.toList()));
    }

    public Chain withIdentifier(char newIdentifier) {
        return new Chain(this, newIdentifier);
    }

    @Override
    public int compareTo(Chain o) {
        return Character.compare(chainIdentifier, o.chainIdentifier);
    }

    public AminoAcidSequence getSequence() {
        return sequence;
    }

    @Override
    public String toString() {
        return "Chain " + chainIdentifier + ":\n" +
                residues.stream()
                        .limit(3)
                        .map(Residue::toString).collect(Collectors.joining("\n")) + "\n...";
    }

    @Override
    public Iterator<Residue> iterator() {
        return residues.iterator();
    }
}
