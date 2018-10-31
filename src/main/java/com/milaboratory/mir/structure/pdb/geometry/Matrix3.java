package com.milaboratory.mir.structure.pdb.geometry;

public final class Matrix3 {
    private final float xx, xy, xz,
            yx, yy, yz,
            zx, zy, zz;

    public Matrix3(float xx, float xy, float xz,
                   float yx, float yy, float yz,
                   float zx, float zy, float zz) {
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

    public float getXX() {
        return xx;
    }

    public float getXY() {
        return xy;
    }

    public float getXZ() {
        return xz;
    }

    public float getYX() {
        return yx;
    }

    public float getYY() {
        return yy;
    }

    public float getYZ() {
        return yz;
    }

    public float getZX() {
        return zx;
    }

    public float getZY() {
        return zy;
    }

    public float getZZ() {
        return zz;
    }
}
