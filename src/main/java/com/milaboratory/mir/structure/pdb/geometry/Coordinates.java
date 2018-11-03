package com.milaboratory.mir.structure.pdb.geometry;

public final class Coordinates {
    private final float x, y, z;

    public Coordinates(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public String toRow() {
        return x + "\t" + y + "\t" + z;
    }

    @Override
    public String toString() {
        return x + "\t" + y + "\t" + z;
    }
}
