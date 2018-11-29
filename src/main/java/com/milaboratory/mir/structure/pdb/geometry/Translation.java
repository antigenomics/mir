package com.milaboratory.mir.structure.pdb.geometry;

public final class Translation implements CoordinateTransformation {

    private final Vector3 offset;

    public Translation(Vector3 offset) {
        this.offset = offset;
    }

    @Override
    public Vector3 apply(Vector3 coordinates) {
        return new Vector3(coordinates.getX() + offset.getX(),
                coordinates.getY() + offset.getY(),
                coordinates.getZ() + offset.getZ());
    }
}
