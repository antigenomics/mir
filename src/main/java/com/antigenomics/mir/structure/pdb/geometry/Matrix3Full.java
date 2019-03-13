package com.antigenomics.mir.structure.pdb.geometry;

public final class Matrix3Full implements Matrix3 {
    private final double xx, xy, xz,
            yx, yy, yz,
            zx, zy, zz;

    public Matrix3Full(double xx, double xy, double xz,
                       double yx, double yy, double yz,
                       double zx, double zy, double zz) {
        this.xx = xx;
        this.xy = xy;
        this.xz = xz;
        this.yx = yx;
        this.yy = yy;
        this.yz = yz;
        this.zx = zx;
        this.zy = zy;
        this.zz = zz;
    }

    public Matrix3Full(double[][] values) {
        this.xx = values[0][0];
        this.xy = values[0][1];
        this.xz = values[0][2];
        this.yx = values[1][0];
        this.yy = values[1][1];
        this.yz = values[1][2];
        this.zx = values[2][0];
        this.zy = values[2][1];
        this.zz = values[2][2];
    }

    public double getXX() {
        return xx;
    }

    public double getXY() {
        return xy;
    }

    public double getXZ() {
        return xz;
    }

    public double getYX() {
        return yx;
    }

    public double getYY() {
        return yy;
    }

    public double getYZ() {
        return yz;
    }

    public double getZX() {
        return zx;
    }

    public double getZY() {
        return zy;
    }

    public double getZZ() {
        return zz;
    }

    @Override
    public String toString() {
        return "[[" + (float) xx + "," + (float) xy + "," + (float) xz + "],\n" +
                "[" + (float) yx + "," + (float) yy + "," + (float) yz + "],\n" +
                "[" + (float) zx + "," + (float) zy + "," + (float) zz + "]]";
    }
}
