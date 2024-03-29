package com.antigenomics.mir.structure.pdb.parser;

import com.antigenomics.mir.structure.pdb.geometry.CoordinateTransformation;
import com.antigenomics.mir.structure.pdb.geometry.Vector3;
import com.antigenomics.mir.structure.pdb.Atom;

public final class RawAtom implements Atom<RawAtom> {
    private final AtomType atomType;
    private final short atomSerialNumber;
    private final AtomName atomName;
    private final char alternativeLocationIdentifier;
    private final ResidueName residueName;
    private final char chainIdentifier;
    private final short residueSequenceNumber;
    private final char residueInsertionCode;
    private final Vector3 coordinates;
    private final float occupancy;
    private final float temperatureFactor;
    private final ElementSymbolWithCharge elementSymbolWithCharge;
    short sequentialResidueSequenceNumber;

    public RawAtom(AtomType atomType,
                   short atomSerialNumber,
                   AtomName atomName,
                   char alternativeLocationIdentifier,
                   ResidueName residueName,
                   char chainIdentifier,
                   short residueSequenceNumber,
                   char residueInsertionCode,
                   float x, float y, float z,
                   float occupancy,
                   float temperatureFactor,
                   ElementSymbolWithCharge elementSymbolWithCharge) {
        this.atomType = atomType;
        this.atomSerialNumber = atomSerialNumber;
        this.atomName = atomName;
        this.alternativeLocationIdentifier = alternativeLocationIdentifier;
        this.residueName = residueName;
        this.chainIdentifier = chainIdentifier;
        this.residueSequenceNumber = residueSequenceNumber;
        this.residueInsertionCode = residueInsertionCode;
        this.coordinates = new Vector3(x, y, z);
        this.occupancy = occupancy;
        this.temperatureFactor = temperatureFactor;
        this.elementSymbolWithCharge = elementSymbolWithCharge;
        this.sequentialResidueSequenceNumber = residueSequenceNumber;
    }

    private RawAtom(RawAtom toCopy, Vector3 newCoordinates) {
        this.atomType = toCopy.atomType;
        this.atomSerialNumber = toCopy.atomSerialNumber;
        this.atomName = toCopy.atomName;
        this.alternativeLocationIdentifier = toCopy.alternativeLocationIdentifier;
        this.residueName = toCopy.residueName;
        this.chainIdentifier = toCopy.chainIdentifier;
        this.residueSequenceNumber = toCopy.residueSequenceNumber;
        this.residueInsertionCode = toCopy.residueInsertionCode;
        this.coordinates = newCoordinates;
        this.occupancy = toCopy.occupancy;
        this.temperatureFactor = toCopy.temperatureFactor;
        this.elementSymbolWithCharge = toCopy.elementSymbolWithCharge;
    }

    RawAtom(RawAtom toCopy, short newAtomSerialNumber) {
        this.atomType = toCopy.atomType;
        this.atomSerialNumber = newAtomSerialNumber;
        this.atomName = toCopy.atomName;
        this.alternativeLocationIdentifier = toCopy.alternativeLocationIdentifier;
        this.residueName = toCopy.residueName;
        this.chainIdentifier = toCopy.chainIdentifier;
        this.residueSequenceNumber = toCopy.residueSequenceNumber;
        this.residueInsertionCode = toCopy.residueInsertionCode;
        this.coordinates = toCopy.coordinates;
        this.occupancy = toCopy.occupancy;
        this.temperatureFactor = toCopy.temperatureFactor;
        this.elementSymbolWithCharge = toCopy.elementSymbolWithCharge;
    }

    RawAtom incrementAtomSerialNumber() {
        return new RawAtom(this, (short) (atomSerialNumber + 1));
    }

    @Override
    public AtomType getAtomType() {
        return atomType;
    }

    @Override
    public short getAtomSerialNumber() {
        return atomSerialNumber;
    }

    @Override
    public AtomName getAtomName() {
        return atomName;
    }

    @Override
    public char getAlternativeLocationIdentifier() {
        return alternativeLocationIdentifier;
    }

    @Override
    public ResidueName getResidueName() {
        return residueName;
    }

    @Override
    public char getChainIdentifier() {
        return chainIdentifier;
    }

    @Override
    public short getResidueSequenceNumber() {
        return residueSequenceNumber;
    }

    @Override
    public char getResidueInsertionCode() {
        return residueInsertionCode;
    }

    @Override
    public Vector3 getCoordinates() {
        return coordinates;
    }

    @Override
    public float getOccupancy() {
        return occupancy;
    }

    @Override
    public float getTemperatureFactor() {
        return temperatureFactor;
    }

    @Override
    public ElementSymbolWithCharge getElementSymbolWithCharge() {
        return elementSymbolWithCharge;
    }

    @Override
    public String toString() {
        return PdbParserUtils.writeAtom(this);
    }

    @Override
    public short getSequentialResidueSequenceNumber() {
        return sequentialResidueSequenceNumber;
    }

    @Override
    public RawAtom applyTransformation(CoordinateTransformation transformation) {
        return new RawAtom(
                this,
                transformation.apply(coordinates)
        );
    }

    @Override
    public int compareTo(RawAtom o) {
        var res = Character.compare(chainIdentifier, o.chainIdentifier);
        if (res != 0) {
            return res;
        }
        res = Short.compare(residueSequenceNumber, o.residueSequenceNumber);
        if (res != 0) {
            return res;
        }
        return Short.compare(atomSerialNumber, o.atomSerialNumber);
    }
}
