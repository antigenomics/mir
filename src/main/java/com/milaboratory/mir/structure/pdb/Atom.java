package com.milaboratory.mir.structure.pdb;

import com.milaboratory.mir.structure.pdb.geometry.CoordinateSet;
import com.milaboratory.mir.structure.pdb.geometry.PointMass;
import com.milaboratory.mir.structure.pdb.parser.*;

public interface Atom<T extends Atom<T>> extends Comparable<T>, CoordinateSet<T>, PointMass {
    AtomType getAtomType();

    short getAtomSerialNumber();

    AtomName getAtomName();

    char getAlternativeLocationIdentifier();

    ResidueName getResidueName();

    char getChainIdentifier();

    short getSequentialResidueSequenceNumber();

    short getResidueSequenceNumber();

    char getResidueInsertionCode();

    float getOccupancy();

    float getTemperatureFactor();

    ElementSymbolWithCharge getElementSymbolWithCharge();

    default double getMass() {
        return getAtomName().getAtomicWeight();
    }
}
