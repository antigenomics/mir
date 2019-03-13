package com.antigenomics.mir.structure.pdb.geometry;

public interface CoordinateSet<T extends CoordinateSet<T>> {
    T applyTransformation(CoordinateTransformation transformation);
}
