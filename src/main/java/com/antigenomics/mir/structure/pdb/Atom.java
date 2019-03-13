package com.antigenomics.mir.structure.pdb;

import com.antigenomics.mir.structure.pdb.geometry.CoordinateSet;
import com.antigenomics.mir.structure.pdb.geometry.PointMass;
import com.antigenomics.mir.structure.pdb.parser.AtomName;
import com.antigenomics.mir.structure.pdb.parser.AtomType;
import com.antigenomics.mir.structure.pdb.parser.ElementSymbolWithCharge;
import com.antigenomics.mir.structure.pdb.parser.ResidueName;
import com.antigenomics.mir.structure.pdb.parser.*;

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
