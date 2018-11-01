package com.milaboratory.mir.structure.pdb.geometry;

import com.milaboratory.mir.structure.pdb.Atom;
import com.milaboratory.mir.structure.pdb.Residue;

public final class MechanicsUtils {
    private static final double M_SQRT3 = Math.sqrt(3),
            DBL_EPSILON = 2.2204460492503131e-16;

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

    // The following two routines are copied from C code
    // ----------------------------------------------------------------------------
    // Numerical diagonalization of 3x3 matrcies
    // Copyright (C) 2006  Joachim Kopp
    // ----------------------------------------------------------------------------
    // This library is free software; you can redistribute it and/or
    // modify it under the terms of the GNU Lesser General Public
    // License as published by the Free Software Foundation; either
    // version 2.1 of the License, or (at your option) any later version.
    //
    // This library is distributed in the hope that it will be useful,
    // but WITHOUT ANY WARRANTY; without even the implied warranty of
    // MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    // Lesser General Public License for more details.
    //
    // You should have received a copy of the GNU Lesser General Public
    // License along with this library; if not, write to the Free Software
    // Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
    // ----------------------------------------------------------------------------

    public static double[] matrix3SymmEigenvalues(Matrix3Symm mat) {
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

        double eig1 = (1.0 / 3.0) * (m - c);

        return new double[]{eig1, eig1 + s, eig1 + c};
    }

    public static Matrix3Full matrix3SymmEigenvectors(Matrix3Symm mat, double[] w) {
        double[][] Q = new double[3][3];
        double[][] A = mat.asArray();

        // Calculate eigenvalues

        double wmax = Math.abs(w[0]; // The eigenvalue of maximum modulus
        wmax = Math.max(wmax, Math.abs(w[1]));
        wmax = Math.max(wmax, Math.abs(w[2]));

        double thresh = 8.0 * DBL_EPSILON * wmax; // Small number used as threshold for floating point comparisons
        thresh *= thresh;


        // Prepare calculation of eigenvectors
        double n0tmp = A[0][1] * A[0][1] + A[0][2] * A[0][2]; // "Templates" for the calculation of n0/n1 - saves a few FLOPS
        double n1tmp = A[0][1] * A[0][1] + A[1][2] * A[1][2];
        Q[0][1] = A[0][1] * A[1][2] - A[0][2] * A[1][1];
        Q[1][1] = A[0][2] * A[0][1] - A[1][2] * A[0][0];
        Q[2][1] = A[0][1] * A[0][1];

        // Calculate first eigenvector by the formula
        //   v[0] = (A - w[0]).e1 x (A - w[0]).e2
        A[0][0] -= w[0];
        A[1][1] -= w[0];
        Q[0][0] = Q[0][1] + A[0][2] * w[0];
        Q[1][0] = Q[1][1] + A[1][2] * w[0];
        Q[2][0] = A[0][0] * A[1][1] - Q[2][1];

        // Squared norm or inverse norm of current eigenvector
        double norm = Q[0][0] * Q[0][0] + Q[1][0] * Q[1][0] + Q[2][0] * Q[2][0];
        // Norm of first and second columns of A
        double n0 = n0tmp + A[0][0] * A[0][0];
        double n1 = n1tmp + A[1][1] * A[1][1];
        double error = n0 * n1; // Estimated maximum roundoff error in some steps

        double f, t;          // Intermediate storage
        if (n0 <= thresh)         // If the first column is zero, then (1,0,0) is an eigenvector
        {
            Q[0][0] = 1.0;
            Q[1][0] = 0.0;
            Q[2][0] = 0.0;
        } else if (n1 <= thresh)    // If the second column is zero, then (0,1,0) is an eigenvector
        {
            Q[0][0] = 0.0;
            Q[1][0] = 1.0;
            Q[2][0] = 0.0;
        } else if (norm < 64.0 * DBL_EPSILON * 64.0 * DBL_EPSILON * error) { // If angle between A[0] and A[1] is too small, don't use
            t = A[0][1] * A[0][1]; // cross product, but calculate v ~ (1, -A0/A1, 0)
            f = -A[0][0] / A[0][1];
            if (A[1][1] * A[1][1] > t) {
                t = A[1][1] * A[1][1];
                f = -A[0][1] / A[1][1];
            }
            if (A[1][2] * A[1][2] > t)
                f = -A[0][2] / A[1][2];
            norm = 1.0 / Math.sqrt(1 + f * f);
            Q[0][0] = norm;
            Q[1][0] = f * norm;
            Q[2][0] = 0.0;
        } else                      // This is the standard branch
        {
            norm = Math.sqrt(1.0 / norm);
            for (int j = 0; j < 3; j++)
                Q[j][0] = Q[j][0] * norm;
        }


        // Prepare calculation of second eigenvector
        t = w[0] - w[1];
        if (Math.abs(t) > 8.0 * DBL_EPSILON * wmax) {
            // For non-degenerate eigenvalue, calculate second eigenvector by the formula
            //   v[1] = (A - w[1]).e1 x (A - w[1]).e2
            A[0][0] += t;
            A[1][1] += t;
            Q[0][1] = Q[0][1] + A[0][2] * w[1];
            Q[1][1] = Q[1][1] + A[1][2] * w[1];
            Q[2][1] = A[0][0] * A[1][1] - Q[2][1];
            norm = Q[0][1] * Q[0][1] + Q[1][1] * Q[1][1] + Q[2][1] * Q[2][1];
            n0 = n0tmp + A[0][0] * A[0][0];
            n1 = n1tmp + A[1][1] * A[1][1];
            error = n0 * n1;

            if (n0 <= thresh)       // If the first column is zero, then (1,0,0) is an eigenvector
            {
                Q[0][1] = 1.0;
                Q[1][1] = 0.0;
                Q[2][1] = 0.0;
            } else if (n1 <= thresh)  // If the second column is zero, then (0,1,0) is an eigenvector
            {
                Q[0][1] = 0.0;
                Q[1][1] = 1.0;
                Q[2][1] = 0.0;
            } else if (norm < 64.0 * DBL_EPSILON * 64.0 * DBL_EPSILON * error) { // If angle between A[0] and A[1] is too small, don't use
                t = A[0][1] * A[0][1];     // cross product, but calculate v ~ (1, -A0/A1, 0)
                f = -A[0][0] / A[0][1];
                if (A[1][1] * A[1][1] > t) {
                    t = A[1][1] * A[1][1];
                    f = -A[0][1] / A[1][1];
                }
                if (A[1][2] * A[1][2] > t)
                    f = -A[0][2] / A[1][2];
                norm = 1.0 / Math.sqrt(1 + f * f);
                Q[0][1] = norm;
                Q[1][1] = f * norm;
                Q[2][1] = 0.0;
            } else {
                norm = Math.sqrt(1.0 / norm);
                for (int j = 0; j < 3; j++)
                    Q[j][1] = Q[j][1] * norm;
            }
        } else {
            // For degenerate eigenvalue, calculate second eigenvector according to
            //   v[1] = v[0] x (A - w[1]).e[i]
            //
            // This would really get to complicated if we could not assume all of A to
            // contain meaningful values.
            A[1][0] = A[0][1];
            A[2][0] = A[0][2];
            A[2][1] = A[1][2];
            A[0][0] += w[0];
            A[1][1] += w[0];
            int i;
            for (i = 0; i < 3; i++) {
                A[i][i] -= w[1];
                n0 = A[0][i] * A[0][i] + A[1][i] * A[1][i] + A[2][i] * A[2][i];
                if (n0 > thresh) {
                    Q[0][1] = Q[1][0] * A[2][i] - Q[2][0] * A[1][i];
                    Q[1][1] = Q[2][0] * A[0][i] - Q[0][0] * A[2][i];
                    Q[2][1] = Q[0][0] * A[1][i] - Q[1][0] * A[0][i];
                    norm = Q[0][1] * Q[0][1] + Q[1][1] * Q[1][1] + Q[2][1] * Q[2][1];
                    if (norm > 256.0 * DBL_EPSILON * 256.0 * DBL_EPSILON * n0) // Accept cross product only if the angle between
                    {                                         // the two vectors was not too small
                        norm = Math.sqrt(1.0 / norm);
                        for (int j = 0; j < 3; j++)
                            Q[j][1] = Q[j][1] * norm;
                        break;
                    }
                }
            }

            if (i == 3)    // This means that any vector orthogonal to v[0] is an EV.
            {
                for (int j = 0; j < 3; j++)
                    if (Q[j][0] != 0.0)                                   // Find nonzero element of v[0] ...
                    {                                                     // ... and swap it with the next one
                        norm = 1.0 / Math.sqrt(Q[j][0] * Q[j][0] + Q[(j + 1) % 3][0] * Q[(j + 1) % 3][0]);
                        Q[j][1] = Q[(j + 1) % 3][0] * norm;
                        Q[(j + 1) % 3][1] = -Q[j][0] * norm;
                        Q[(j + 2) % 3][1] = 0.0;
                        break;
                    }
            }
        }


        // Calculate third eigenvector according to
        //   v[2] = v[0] x v[1]
        Q[0][2] = Q[1][0] * Q[2][1] - Q[2][0] * Q[1][1];
        Q[1][2] = Q[2][0] * Q[0][1] - Q[0][0] * Q[2][1];
        Q[2][2] = Q[0][0] * Q[1][1] - Q[1][0] * Q[0][1];

        return new Matrix3Full(
                (float) Q[0][0], (float) Q[0][1], (float) Q[0][2],
                (float) Q[1][0], (float) Q[1][1], (float) Q[1][2],
                (float) Q[2][0], (float) Q[2][1], (float) Q[2][2]
        );
    }
}
