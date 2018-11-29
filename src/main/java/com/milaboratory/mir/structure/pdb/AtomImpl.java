package com.milaboratory.mir.structure.pdb;

import com.milaboratory.mir.structure.pdb.geometry.CoordinateTransformation;
import com.milaboratory.mir.structure.pdb.geometry.Vector3;
import com.milaboratory.mir.structure.pdb.parser.*;

public final class AtomImpl implements Atom<AtomImpl> {
    private final Residue parent;
    private final AtomType atomType;
    private final short atomSerialNumber;
    private final AtomName atomName;
    private final char alternativeLocationIdentifier;
    private final Vector3 coordinates;
    private final float occupancy;
    private final float temperatureFactor;
    private final ElementSymbolWithCharge elementSymbolWithCharge;

    public AtomImpl(Residue parent,
                    AtomType atomType,
                    short atomSerialNumber,
                    AtomName atomName,
                    char alternativeLocationIdentifier,
                    Vector3 coordinates,
                    float occupancy,
                    float temperatureFactor,
                    ElementSymbolWithCharge elementSymbolWithCharge) {
        this.parent = parent;
        this.atomType = atomType;
        this.atomSerialNumber = atomSerialNumber;
        this.atomName = atomName;
        this.alternativeLocationIdentifier = alternativeLocationIdentifier;
        this.coordinates = coordinates;
        this.occupancy = occupancy;
        this.temperatureFactor = temperatureFactor;
        this.elementSymbolWithCharge = elementSymbolWithCharge;
    }

    private AtomImpl(AtomImpl toCopy, Vector3 newCoordinates) {
        this.parent = toCopy.parent;
        this.atomType = toCopy.atomType;
        this.atomSerialNumber = toCopy.atomSerialNumber;
        this.atomName = toCopy.atomName;
        this.alternativeLocationIdentifier = toCopy.alternativeLocationIdentifier;
        this.coordinates = newCoordinates;
        this.occupancy = toCopy.occupancy;
        this.temperatureFactor = toCopy.temperatureFactor;
        this.elementSymbolWithCharge = toCopy.elementSymbolWithCharge;
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
        return parent.getResidueName();
    }

    @Override
    public char getChainIdentifier() {
        return parent.getChainIdentifier();
    }

    @Override
    public short getResidueSequenceNumber() {
        return parent.getResidueSequenceNumber();
    }

    @Override
    public short getSequentialResidueSequenceNumber() {
        return parent.getSequentialResidueSequenceNumber();
    }

    @Override
    public char getResidueInsertionCode() {
        return parent.getResidueInsertionCode();
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
    public Vector3 getCoordinates() {
        return coordinates;
    }

    @Override
    public ElementSymbolWithCharge getElementSymbolWithCharge() {
        return elementSymbolWithCharge;
    }

    @Override
    public AtomImpl applyTransformation(CoordinateTransformation transformation) {
        return new AtomImpl(this, transformation.apply(coordinates));
    }

    public Residue getParent() {
        return parent;
    }

    @Override
    public int compareTo(AtomImpl o) {
        var res = parent.compareTo(o.parent);
        if (res != 0) {
            return res;
        }
        return Short.compare(atomSerialNumber, o.atomSerialNumber);
    }

    @Override
    public String toString() {
        return PdbParserUtils.writeAtom(this);
    }
}
