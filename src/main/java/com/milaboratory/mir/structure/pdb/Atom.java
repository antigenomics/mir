package com.milaboratory.mir.structure.pdb;

import com.milaboratory.mir.structure.pdb.geometry.CoordinateSet;
import com.milaboratory.mir.structure.pdb.geometry.Coordinates;
import com.milaboratory.mir.structure.pdb.parser.*;

public interface Atom<T extends Atom<T>> extends Comparable<T>, CoordinateSet<T> {
    AtomType getAtomType();

    short getAtomSerialNumber();

    AtomName getAtomName();

    char getAlternativeLocationIdentifier();

    ResidueName getResidueName();

    char getChainIdentifier();

    short getResidueSequenceNumber();

    char getResidueInsertionCode();

    float getOccupancy();

    float getTemperatureFactor();

    Coordinates getCoordinates();

    ElementSymbolWithCharge getElementSymbolWithCharge();
}
