package com.milaboratory.mir.structure.pdb.geometry;

import com.milaboratory.mir.structure.pdb.Atom;
import com.milaboratory.mir.structure.pdb.Residue;

public final class GeometryUtils {
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
        return (float) Math.acos(scalarProduct(u, v) / norm(u) / norm(v));
    }

    public static Coordinates crossProduct(Coordinates u, Coordinates v) {
        return new Coordinates(u.getY() * v.getZ() - u.getZ() * v.getY(),
                u.getZ() * v.getX() - u.getX() * v.getZ(),
                u.getX() * v.getY() - u.getY() * v.getX());
    }

    public static float torsionAngle(Coordinates x0, Coordinates x1, Coordinates x2, Coordinates x3) {
        final Coordinates t0 = vector(x0, x1), t1 = vector(x1, x2), t2 = vector(x2, x3);
        final Coordinates b1 = crossProduct(t0, t1), b2 = crossProduct(t1, t2);
        return angle(b1, b2);
    }

    public static float curvatureAngle(Coordinates x0, Coordinates x1, Coordinates x2) {
        final Coordinates t0 = vector(x0, x1), t1 = vector(x1, x2);
        return angle(t0, t1);
    }

    public static Coordinates centerOfMass(Iterable<Residue> residues) {
        float x = 0, y = 0, z = 0;

        for (Residue residue : residues) {
            for (Atom atom : residue) {
                float wt = atom.getAtomName().getAtomicWeight();
                Coordinates coordinates = atom.getCoordinates();
                x += coordinates.getX() * wt;
                y += coordinates.getY() * wt;
                z += coordinates.getZ() * wt;
            }
        }

        return new Coordinates(x, y, z);
    }

    /*
    % Given a real symmetric 3x3 matrix A, compute the eigenvalues
% Note that acos and cos operate on angles in radians

p1 = A(1,2)^2 + A(1,3)^2 + A(2,3)^2
if (p1 == 0)
   % A is diagonal.
   eig1 = A(1,1)
   eig2 = A(2,2)
   eig3 = A(3,3)
else
   q = trace(A)/3               % trace(A) is the sum of all diagonal values
   p2 = (A(1,1) - q)^2 + (A(2,2) - q)^2 + (A(3,3) - q)^2 + 2 * p1
   p = sqrt(p2 / 6)
   B = (1 / p) * (A - q * I)    % I is the identity matrix
   r = det(B) / 2

   % In exact arithmetic for a symmetric matrix  -1 <= r <= 1
   % but computation error can leave it slightly outside this range.
   if (r <= -1)
      phi = pi / 3
   elseif (r >= 1)
      phi = 0
   else
      phi = acos(r) / 3
   end

   % the eigenvalues satisfy eig3 <= eig2 <= eig1
   eig1 = q + 2 * p * cos(phi)
   eig3 = q + 2 * p * cos(phi + (2*pi/3))
   eig2 = 3 * q - eig1 - eig3     % since trace(A) = eig1 + eig2 + eig3
end
     */
}
