package com.milaboratory.mir.structure.pdb.geometry;

public interface Matrix3 {
    float getXX();

    float getXY();

    float getXZ();

    float getYX();

    float getYY();

    float getYZ();

    float getZX();

    float getZY();

    float getZZ();

    default float trace() {
        return getXX() + getYY() + getZZ();
    }
}
