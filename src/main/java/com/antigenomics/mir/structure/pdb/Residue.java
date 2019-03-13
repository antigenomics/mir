package com.antigenomics.mir.structure.pdb;

import com.antigenomics.mir.structure.pdb.geometry.CoordinateSet;
import com.antigenomics.mir.structure.pdb.geometry.CoordinateTransformation;
import com.antigenomics.mir.structure.pdb.parser.RawAtom;
import com.antigenomics.mir.structure.pdb.parser.ResidueName;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public final class Residue implements Comparable<Residue>, CoordinateSet<Residue>, Iterable<AtomImpl> {
    private final Chain parent;
    private final ResidueName residueName;
    private final short sequentialResidueSequenceNumber;
    private final short residueSequenceNumber;
    private final char residueInsertionCode;
    private final List<AtomImpl> atoms;

    Residue(Chain parent, List<RawAtom> rawAtoms) {
        if (rawAtoms.isEmpty()) {
            throw new IllegalArgumentException("Empty atom list");
        }
        this.parent = parent;
        var firstAtom = rawAtoms.get(0);
        this.residueName = firstAtom.getResidueName();
        this.sequentialResidueSequenceNumber = firstAtom.getSequentialResidueSequenceNumber();
        this.residueSequenceNumber = firstAtom.getResidueSequenceNumber();
        this.residueInsertionCode = firstAtom.getResidueInsertionCode();
        this.atoms = rawAtoms.stream().map(
                x -> {
                    if (x.getChainIdentifier() != getChainIdentifier()) {
                        throw new IllegalArgumentException("Wrong chain identifier " + x);
                    }
                    if (x.getResidueName() != residueName) {
                        throw new IllegalArgumentException("Wrong residue name " + x);
                    }
                    if (x.getSequentialResidueSequenceNumber() != sequentialResidueSequenceNumber) {
                        throw new IllegalArgumentException("Wrong shifted residue index " + x);
                    }
                    if (x.getResidueSequenceNumber() != residueSequenceNumber) {
                        throw new IllegalArgumentException("Wrong original residue index " + x);
                    }
                    if (x.getResidueInsertionCode() != residueInsertionCode) {
                        throw new IllegalArgumentException("Wrong residue insertion code " + x);
                    }
                    return new AtomImpl(this,
                            x.getAtomType(),
                            x.getAtomSerialNumber(),
                            x.getAtomName(),
                            x.getAlternativeLocationIdentifier(),
                            x.getCoordinates(),
                            x.getOccupancy(),
                            x.getTemperatureFactor(),
                            x.getElementSymbolWithCharge());
                }
        ).collect(Collectors.toUnmodifiableList());
    }

    public Residue(Chain parent, ResidueName residueName,
                   short sequentialResidueSequenceNumber,
                   short residueSequenceNumber, char residueInsertionCode,
                   List<AtomImpl> atoms) {
        this.parent = parent;
        this.residueName = residueName;
        this.sequentialResidueSequenceNumber = sequentialResidueSequenceNumber;
        this.residueSequenceNumber = residueSequenceNumber;
        this.residueInsertionCode = residueInsertionCode;
        this.atoms = Collections.unmodifiableList(atoms);
    }

    private Residue(Residue toCopy, List<AtomImpl> newAtoms) {
        this.parent = toCopy.parent;
        this.residueName = toCopy.residueName;
        this.sequentialResidueSequenceNumber = toCopy.sequentialResidueSequenceNumber;
        this.residueSequenceNumber = toCopy.residueSequenceNumber;
        this.residueInsertionCode = toCopy.residueInsertionCode;
        this.atoms = Collections.unmodifiableList(newAtoms);
    }

    public Chain getParent() {
        return parent;
    }

    public char getChainIdentifier() {
        return parent.getChainIdentifier();
    }

    public ResidueName getResidueName() {
        return residueName;
    }

    public short getSequentialResidueSequenceNumber() {
        return sequentialResidueSequenceNumber;
    }

    public short getResidueSequenceNumber() {
        return residueSequenceNumber;
    }

    public char getResidueInsertionCode() {
        return residueInsertionCode;
    }

    public List<AtomImpl> getAtoms() {
        return atoms;
    }

    @Override
    public Residue applyTransformation(CoordinateTransformation transformation) {
        return new Residue(this, atoms.stream()
                .map(x -> x.applyTransformation(transformation)).collect(Collectors.toList()));
    }

    @Override
    public int compareTo(Residue o) {
        var res = parent.compareTo(o.parent);
        if (res != 0) {
            return res;
        }
        return Short.compare(sequentialResidueSequenceNumber, o.sequentialResidueSequenceNumber);
    }

    public String toShortString() {
        return getChainIdentifier() + "\t" +
                sequentialResidueSequenceNumber + "\t(" +
                residueSequenceNumber +
                residueInsertionCode + ")\t" +
                residueName.getLetter();
    }

    @Override
    public String toString() {
        return atoms
                .stream()
                .limit(5)
                .map(Atom::toString).collect(Collectors.joining("\n")) + "\n...";
    }

    @Override
    public Iterator<AtomImpl> iterator() {
        return atoms.iterator();
    }
}
