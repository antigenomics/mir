package com.milaboratory.mir.structure.pdb.geometry;

public final class GeometryUtils {
    public static float distance(Coordinates u, Coordinates v) {
        return norm(vector(u, v));
    }

    public static Coordinates vector(Coordinates start, Coordinates end) {
        return new Coordinates(end.getX() - start.getX(),
                end.getY() - start.getY(),
                end.getZ() - start.getZ());
    }

    public static Coordinates add(Coordinates u, Coordinates v) {
        return new Coordinates(u.getX() + v.getX(),
                u.getY() + v.getY(),
                u.getZ() + v.getZ());
    }

    public static Coordinates scale(Coordinates u, float scalar) {
        return new Coordinates(u.getX() * scalar,
                u.getY() * scalar,
                u.getZ() * scalar);
    }

    public static float norm(Coordinates v) {
        return (float) Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY() + v.getZ() * v.getZ());
    }

    public static float scalarProduct(Coordinates u, Coordinates v) {
        return (u.getX() * v.getX() + u.getY() * v.getY() + u.getZ() * v.getZ());
    }

    public static float angle(Coordinates u, Coordinates v) {
        float x = scalarProduct(u, v) / norm(u) / norm(v);
        if (x < -1) {
            return (float) Math.PI;
        } else if (x > 1) {
            return 0.0f;
        }
        return (float) Math.acos(x);
    }

    public static Coordinates crossProduct(Coordinates u, Coordinates v) {
        return new Coordinates(u.getY() * v.getZ() - u.getZ() * v.getY(),
                u.getZ() * v.getX() - u.getX() * v.getZ(),
                u.getX() * v.getY() - u.getY() * v.getX());
    }

    public static float torsionAngle(Coordinates x0, Coordinates x1, Coordinates x2, Coordinates x3) {
        final Coordinates t0 = vector(x0, x1), t1 = vector(x1, x2), t2 = vector(x2, x3);
        final Coordinates b1 = crossProduct(t0, t1), b2 = crossProduct(t1, t2), n1 = crossProduct(b1, t1);
        float angle = angle(b1, b2), direction = scalarProduct(n1, b2);
        return direction < 0 ? angle : -angle; // sign so we get right (not transposed) Ramachandran plot
    }

    public static float curvatureAngle(Coordinates x0, Coordinates x1, Coordinates x2) {
        final Coordinates t0 = vector(x0, x1), t1 = vector(x1, x2);
        return angle(t0, t1);
    }
}
