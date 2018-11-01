package com.milaboratory.mir.structure.pdb.geometry;

public final class Matrix3Symm implements Matrix3 {
    private final float xx, xy, xz, yy, yz, zz;

    public Matrix3Symm(float xx, float xy, float xz, float yy, float yz, float zz) {
        this.xx = xx;
        this.xy = xy;
        this.xz = xz;
        this.yy = yy;
        this.yz = yz;
        this.zz = zz;
    }

    @Override
    public float getXX() {
        return xx;
    }

    @Override
    public float getXY() {
        return xy;
    }

    @Override
    public float getXZ() {
        return xz;
    }

    @Override
    public float getYX() {
        return xy;
    }

    @Override
    public float getYY() {
        return yy;
    }

    @Override
    public float getYZ() {
        return yz;
    }

    @Override
    public float getZX() {
        return xz;
    }

    @Override
    public float getZY() {
        return yz;
    }

    @Override
    public float getZZ() {
        return zz;
    }
}
