package com.milaboratory.mir.structure.pdb;

import com.milaboratory.core.Range;
import com.milaboratory.core.sequence.AminoAcidAlphabet;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.mappers.markup.SequenceRegion;
import com.milaboratory.mir.structure.pdb.geometry.CoordinateSet;
import com.milaboratory.mir.structure.pdb.geometry.CoordinateTransformation;
import com.milaboratory.mir.structure.pdb.parser.RawAtom;

import java.util.*;
import java.util.stream.Collectors;

public class Chain implements Comparable<Chain>, CoordinateSet<Chain> {
    private final char chainIdentifier;
    private final Map<Short, Residue> residues;
    private final AminoAcidSequence sequence;
    private final Range pdbNumberingRange;

    public Chain(char chainIdentifier, Map<Short, List<RawAtom>> residueMap) {
        if (residueMap.isEmpty()) {
            throw new IllegalArgumentException("Empty residue map");
        }
        this.chainIdentifier = chainIdentifier;
        this.residues = CommonUtils.map2map(
                residueMap,
                Map.Entry::getKey,
                x -> new Residue(this, x.getValue())
        );
        this.pdbNumberingRange = getRange(residues.values());
        this.sequence = getSequence(residues.values(), pdbNumberingRange);
    }

    Chain(Chain toCopy, Map<Short, Residue> newResidues) {
        this.chainIdentifier = toCopy.chainIdentifier;
        this.residues = newResidues;
        this.pdbNumberingRange = getRange(residues.values());
        this.sequence = getSequence(newResidues.values(), pdbNumberingRange);
    }

    private static Range getRange(Collection<Residue> residues) {
        int minResidue = Integer.MAX_VALUE, maxResidue = Integer.MIN_VALUE;
        for (Residue residue : residues) {
            minResidue = Math.min(minResidue, residue.getResidueSequenceNumber());
            maxResidue = Math.max(maxResidue, residue.getResidueSequenceNumber());
        }
        return new Range(minResidue, maxResidue + 1);
    }

    private static AminoAcidSequence getSequence(Collection<Residue> residues, Range pdbNumberingRange) {
        byte[] aas = new byte[pdbNumberingRange.length()];
        Arrays.fill(aas, AminoAcidAlphabet.X);
        for (Residue residue : residues) {
            short residueSequenceNumber = residue.getResidueSequenceNumber();
            byte code = residue.getResidueName().getCode();
            aas[residueSequenceNumber - pdbNumberingRange.getFrom()] = code;
        }
        return new AminoAcidSequence(aas);
    }

    public <E extends Enum<E>> ChainRegion<E> getRegion(SequenceRegion<AminoAcidSequence, E> region) {
        return new ChainRegion<>(
                this,
                residues.values().stream().filter(x -> {
                    int pos = x.getResidueSequenceNumber() - pdbNumberingRange.getFrom();
                    return region.contains(pos);
                }).collect(Collectors.toMap(Residue::getResidueSequenceNumber, x -> x)),
                region);
    }

    public char getChainIdentifier() {
        return chainIdentifier;
    }

    public Residue getResidue(short index) {
        var res = residues.get(index);
        if (res == null) {
            throw new IllegalArgumentException("Residue is not present in structure");
        }
        return res;
    }

    public Set<Short> getResidueIndices() {
        return Collections.unmodifiableSet(residues.keySet());
    }

    public Collection<Residue> getResidues() {
        return Collections.unmodifiableCollection(residues.values());
    }

    @Override
    public Chain applyTransformation(CoordinateTransformation transformation) {
        return new Chain(this,
                CommonUtils.map2map(
                        residues,
                        Map.Entry::getKey,
                        x -> x.getValue().applyTransformation(transformation)
                ));
    }

    @Override
    public int compareTo(Chain o) {
        return Character.compare(chainIdentifier, o.chainIdentifier);
    }

    public AminoAcidSequence getSequence() {
        return sequence;
    }

    public Range getPdbNumberingRange() {
        return pdbNumberingRange;
    }

    @Override
    public String toString() {
        return "Chain " + chainIdentifier + ":\n" +
                residues
                        .values()
                        .stream()
                        .limit(3)
                        .map(Residue::toString).collect(Collectors.joining("\n")) + "\n...";
    }
}
