package com.milaboratory.mir.structure.pdb;

import com.milaboratory.mir.structure.pdb.parser.PdbParserUtils;

public class Atom implements Comparable<Atom> {
    private final AtomType atomType;
    private final short atomSerialNumber;
    private final AtomName atomName;
    private final char alternativeLocationIdentifier;
    private final ResidueName residueName;
    private final char chainIdentifier;
    private final short residueSequenceNumber;
    private final char residueInsertionCode;
    private final float x, y, z;
    private final float occupancy;
    private final float temperatureFactor;
    private final ElementSymbolWithCharge elementSymbolWithCharge;

    public Atom(AtomType atomType,
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
        this.x = x;
        this.y = y;
        this.z = z;
        this.occupancy = occupancy;
        this.temperatureFactor = temperatureFactor;
        this.elementSymbolWithCharge = elementSymbolWithCharge;
    }

    public AtomType getAtomType() {
        return atomType;
    }

    public short getAtomSerialNumber() {
        return atomSerialNumber;
    }

    public AtomName getAtomName() {
        return atomName;
    }

    public char getAlternativeLocationIdentifier() {
        return alternativeLocationIdentifier;
    }

    public ResidueName getResidueName() {
        return residueName;
    }

    public char getChainIdentifier() {
        return chainIdentifier;
    }

    public short getResidueSequenceNumber() {
        return residueSequenceNumber;
    }

    public char getResidueInsertionCode() {
        return residueInsertionCode;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getOccupancy() {
        return occupancy;
    }

    public float getTemperatureFactor() {
        return temperatureFactor;
    }

    public ElementSymbolWithCharge getElementSymbolWithCharge() {
        return elementSymbolWithCharge;
    }

    @Override
    public String toString() {
        return PdbParserUtils.writeAtom(this);
    }

    @Override
    public int compareTo(Atom o) {
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
