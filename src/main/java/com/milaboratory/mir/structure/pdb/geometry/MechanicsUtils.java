package com.milaboratory.mir.structure.pdb.geometry;

import com.milaboratory.mir.structure.pdb.Atom;
import com.milaboratory.mir.structure.pdb.Residue;

public final class MechanicsUtils {
    private static final double M_SQRT3 = Math.sqrt(3);

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

    public static Matrix3Symm inertiaTensor(Iterable<Residue> residues) {
        float xCm = 0, yCm = 0, zCm = 0,
                xxI = 0, xyI = 0, xzI = 0,
                yyI = 0, yzI = 0,
                zzI = 0;
        float mTot = 0;

        for (Residue residue : residues) {
            for (Atom atom : residue) {
                float wt = atom.getAtomName().getAtomicWeight();
                Coordinates coordinates = atom.getCoordinates();

                float x = coordinates.getX(),
                        y = coordinates.getY(),
                        z = coordinates.getZ();
                xCm += x * wt;
                yCm += y * wt;
                zCm += z * wt;
                mTot += wt;

                xxI += wt * (y * y + z * z);
                xyI -= wt * x * y;
                xzI -= wt * x * z;

                yyI += wt * (z * z + x * x);
                yzI -= wt * y * z;

                zzI += wt * (x * x + y * y);
            }
        }

        xxI -= (yCm * yCm + zCm * zCm) / mTot;
        xyI += (xCm * yCm) / mTot;
        xzI += (xCm * zCm) / mTot;

        yyI -= (xCm * xCm + zCm * zCm) / mTot;
        yzI += (yCm * zCm) / mTot;

        zzI -= (xCm * xCm + yCm * yCm) / mTot;

        return new Matrix3Symm(xxI, xyI, xzI, yyI, yzI, zzI);
    }

    public static Coordinates matrix3SymmEigenvalues(Matrix3Symm mat) {
        // Determine coefficients of characteristic poynomial. We write
        //       | a   d   f  |
        //  A =  | d*  b   e  |
        //       | f*  e*  c  |
        double de = mat.getXY() * mat.getYZ(); // d * e
        double dd = mat.getXY() * mat.getXY(); // d^2
        double ee = mat.getYZ() * mat.getYZ(); // e^2
        double ff = mat.getXZ() * mat.getXZ(); // f^2

        double m = mat.trace();

        // a*b + a*c + b*c - d^2 - e^2 - f^2
        double c1 = (mat.getXX() * mat.getYY() + mat.getXX() * mat.getZZ() + mat.getYY() * mat.getZZ())
                - (dd + ee + ff);

        // c*d^2 + a*e^2 + b*f^2 - a*b*c - 2*f*d*e
        double c0 = mat.getZZ() * dd + mat.getXX() * ee + mat.getYY() * ff - mat.getXX() * mat.getYY() * mat.getZZ()
                - 2.0 * mat.getXZ() * de;

        double p = m * m - 3.0 * c1;
        double q = m * (p - (3.0 / 2.0) * c1) - (27.0 / 2.0) * c0;
        double sqrtP = Math.sqrt(Math.abs(p));

        double phi = 27.0 * (0.25 * c1 * c1 * (p - c1) + c0 * (q + 27.0 / 4.0 * c0));
        phi = (1.0 / 3.0) * Math.atan2(Math.sqrt(Math.abs(phi)), q);

        double c = sqrtP * Math.cos(phi);
        double s = (1.0 / M_SQRT3) * sqrtP * Math.sin(phi);

        float eig1 = (float) ((1.0 / 3.0) * (m - c));
        float eig2 = (float) (eig1 + s);
        float eig0 = (float) (eig1 + c);


        return new Coordinates(eig0, eig1, eig2);
    }
}
