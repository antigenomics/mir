package com.milaboratory.mir.structure.pdb.geometry;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface Matrix3 {
    double getXX();

    double getXY();

    double getXZ();

    double getYX();

    double getYY();

    double getYZ();

    double getZX();

    double getZY();

    double getZZ();

    default double trace() {
        return getXX() + getYY() + getZZ();
    }

    default double[][] asArray() {
        return new double[][]{
                new double[]{getXX(), getXY(), getXZ()},
                new double[]{getYX(), getYY(), getYZ()},
                new double[]{getZX(), getZY(), getZZ()}
        };
    }

    default Vector3[] asVectors() {
        return new Vector3[]{
                new Vector3(getXX(), getYX(), getZX()),
                new Vector3(getXY(), getYY(), getZY()),
                new Vector3(getXZ(), getYZ(), getZZ())
        };
    }
}
