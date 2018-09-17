package com.milaboratory.mir.structure.pdb.geometry;

public interface CoordinateSet<T extends CoordinateSet<T>> {
    T applyTransformation(CoordinateTransformation transformation);
}
