package com.antigenomics.mir.structure.pdb.geometry;

public final class Vector3 {
    // todo: all to double
    private final double x, y, z;

    public Vector3(double... values) {
        this.x = values[0];
        this.y = values[1];
        this.z = values[2];
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public String toRow() {
        return (float) x + "\t" + (float) y + "\t" + (float) z;
    }

    @Override
    public String toString() {
        return "[" + (float) x + ", " + (float) y + ", " + (float) z + "]";
    }
}
