package com.milaboratory.mir.structure.pdb.geometry;

public final class GeometryUtils {
    public static double distance(Vector3 u, Vector3 v) {
        return norm(vector(u, v));
    }

    public static Vector3 vector(Vector3 start, Vector3 end) {
        return new Vector3(end.getX() - start.getX(),
                end.getY() - start.getY(),
                end.getZ() - start.getZ());
    }

    public static Vector3 add(Vector3 u, Vector3 v) {
        return new Vector3(u.getX() + v.getX(),
                u.getY() + v.getY(),
                u.getZ() + v.getZ());
    }

    public static Vector3 scale(Vector3 u, float scalar) {
        return new Vector3(u.getX() * scalar,
                u.getY() * scalar,
                u.getZ() * scalar);
    }

    public static double norm(Vector3 v) {
        return (float) Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY() + v.getZ() * v.getZ());
    }

    public static double scalarProduct(Vector3 u, Vector3 v) {
        return (u.getX() * v.getX() + u.getY() * v.getY() + u.getZ() * v.getZ());
    }

    public static double angle(Vector3 u, Vector3 v) {
        double x = scalarProduct(u, v) / norm(u) / norm(v);
        if (x < -1) {
            return (float) Math.PI;
        } else if (x > 1) {
            return 0.0f;
        }
        return (float) Math.acos(x);
    }

    public static Vector3 crossProduct(Vector3 u, Vector3 v) {
        return new Vector3(u.getY() * v.getZ() - u.getZ() * v.getY(),
                u.getZ() * v.getX() - u.getX() * v.getZ(),
                u.getX() * v.getY() - u.getY() * v.getX());
    }

    public static double torsionAngle(Vector3 x0, Vector3 x1, Vector3 x2, Vector3 x3) {
        final Vector3 t0 = vector(x0, x1), t1 = vector(x1, x2), t2 = vector(x2, x3);
        final Vector3 b1 = crossProduct(t0, t1), b2 = crossProduct(t1, t2), n1 = crossProduct(b1, t1);
        double angle = angle(b1, b2), direction = scalarProduct(n1, b2);
        return direction < 0 ? angle : -angle; // sign so we get right (not transposed) Ramachandran plot
    }

    public static double curvatureAngle(Vector3 x0, Vector3 x1, Vector3 x2) {
        final Vector3 t0 = vector(x0, x1), t1 = vector(x1, x2);
        return angle(t0, t1);
    }
}
