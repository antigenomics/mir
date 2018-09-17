package com.milaboratory.mir.structure.pdb.geometry;

import java.util.function.Function;

@FunctionalInterface
public interface CoordinateTransformation extends Function<Coordinates, Coordinates> {
}
