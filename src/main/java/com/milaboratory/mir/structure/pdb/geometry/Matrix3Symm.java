package com.milaboratory.mir.structure.pdb.geometry;

public final class Matrix3Symm implements Matrix3 {
    private final double xx, xy, xz, yy, yz, zz;

    public Matrix3Symm(double xx, double xy, double xz, double yy, double yz, double zz) {
        this.xx = xx;
        this.xy = xy;
        this.xz = xz;
        this.yy = yy;
        this.yz = yz;
        this.zz = zz;
    }

    @Override
    public double getXX() {
        return xx;
    }

    @Override
    public double getXY() {
        return xy;
    }

    @Override
    public double getXZ() {
        return xz;
    }

    @Override
    public double getYX() {
        return xy;
    }

    @Override
    public double getYY() {
        return yy;
    }

    @Override
    public double getYZ() {
        return yz;
    }

    @Override
    public double getZX() {
        return xz;
    }

    @Override
    public double getZY() {
        return yz;
    }

    @Override
    public double getZZ() {
        return zz;
    }

    @Override
    public String toString() {
        return "[[" + (float) xx + "," + (float) xy + "," + (float) xz + "],\n" +
                "[" + "_," + "_," + (float) yz + "],\n" +
                "[" + "_," + "_," + (float) zz + "]]";
    }
}
