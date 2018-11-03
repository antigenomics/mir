package com.milaboratory.mir.structure.pdb.geometry;

import java.util.Arrays;
import java.util.List;

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

    default Coordinates[] asVectors() {
        return new Coordinates[]{
                new Coordinates(getXX(), getYX(), getZX()),
                new Coordinates(getXY(), getYY(), getZY()),
                new Coordinates(getXZ(), getYZ(), getZZ())
        };
    }
}
