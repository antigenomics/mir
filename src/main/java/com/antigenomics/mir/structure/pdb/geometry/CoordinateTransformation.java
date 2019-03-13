package com.antigenomics.mir.structure.pdb.geometry;

import java.util.function.Function;

@FunctionalInterface
public interface CoordinateTransformation extends Function<Vector3, Vector3> {
}
