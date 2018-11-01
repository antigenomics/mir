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

    default double[][] asArray() {
        return new double[][]{
                new double[]{getXX(), getXY(), getXZ()},
                new double[]{getYX(), getYY(), getYZ()},
                new double[]{getZX(), getZY(), getZZ()}
        };
    }
}
