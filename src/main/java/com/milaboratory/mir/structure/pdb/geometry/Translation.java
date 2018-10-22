package com.milaboratory.mir.structure.pdb.geometry;

public final class Translation implements CoordinateTransformation {

    private final Coordinates offset;

    public Translation(Coordinates offset) {
        this.offset = offset;
    }

    @Override
    public Coordinates apply(Coordinates coordinates) {
        return new Coordinates(coordinates.getX() + offset.getX(),
                coordinates.getY() + offset.getY(),
                coordinates.getZ() + offset.getZ());
    }
}
