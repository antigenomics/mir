package com.antigenomics.mir.structure.pdb.geometry.summary;

import com.antigenomics.mir.structure.pdb.geometry.Matrix3Full;
import com.antigenomics.mir.structure.pdb.geometry.Vector3;

public final class PrincipalAxes {
    private static final double M_SQRT3 = Math.sqrt(3), DBL_EPSILON = 2.2204460492503131e-16;

    private final InertiaTensor inertiaTensor;
    private final Vector3 principalMoments,
            principalAxis1, principalAxis2, principalAxis3;

    public PrincipalAxes(InertiaTensor inertiaTensor) {
        this.inertiaTensor = inertiaTensor;

        double[][] matI = inertiaTensor.getInertiaTensorMatrix().asArray();
        double[] eig = dsyevc3(matI);
        double[][] eiv = dsyevv3(matI, eig);
        this.principalMoments = new Vector3(eig);

        var eigenvectors = new Matrix3Full(eiv).asVectors();
        this.principalAxis1 = eigenvectors[0];
        this.principalAxis2 = eigenvectors[1];
        this.principalAxis3 = eigenvectors[2];
    }

    public InertiaTensor getInertiaTensor() {
        return inertiaTensor;
    }

    public Vector3 getPrincipalMoments() {
        return principalMoments;
    }

    public Vector3 getPrincipalAxis1() {
        return principalAxis1;
    }

    public Vector3 getPrincipalAxis2() {
        return principalAxis2;
    }

    public Vector3 getPrincipalAxis3() {
        return principalAxis3;
    }

    @Override
    public String toString() {
        return inertiaTensor.toString() + "\n" +
                "eigI=" + principalMoments.toString() + "\n" +
                "PR1=" + principalAxis1.toString() + "\n" +
                "PR2=" + principalAxis2.toString() + "\n" +
                "PR3=" + principalAxis3.toString() + "\n";
    }

    private static double SQR(double x) {
        return x * x;
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

    // ----------------------------------------------------------------------------
    // Calculates the eigenvalues of a symmetric 3x3 matrix A using Cardano's
    // analytical algorithm.
    // Only the diagonal and upper triangular parts of A are accessed. The access
    // is read-only.
    // ----------------------------------------------------------------------------
    // Parameters:
    //   A: The symmetric input matrix
    //   w: Storage buffer for eigenvalues
    // ----------------------------------------------------------------------------
    // Return value:
    //   0: Success
    //  -1: Error
    // ----------------------------------------------------------------------------
    static double[] dsyevc3(double[][] A) {
        double[] w = new double[3];
        double m, c1, c0;

        // Determine coefficients of characteristic poynomial. We write
        //       | a   d   f  |
        //  A =  | d*  b   e  |
        //       | f*  e*  c  |
        double de = A[0][1] * A[1][2];                                    // d * e
        double dd = SQR(A[0][1]);                                         // d^2
        double ee = SQR(A[1][2]);                                         // e^2
        double ff = SQR(A[0][2]);                                         // f^2
        m = A[0][0] + A[1][1] + A[2][2];
        c1 = (A[0][0] * A[1][1] + A[0][0] * A[2][2] + A[1][1] * A[2][2])        // a*b + a*c + b*c - d^2 - e^2 - f^2
                - (dd + ee + ff);
        c0 = A[2][2] * dd + A[0][0] * ee + A[1][1] * ff - A[0][0] * A[1][1] * A[2][2]
                - 2.0 * A[0][2] * de;                                     // c*d^2 + a*e^2 + b*f^2 - a*b*c - 2*f*d*e)

        double p, sqrt_p, q, c, s, phi;
        p = SQR(m) - 3.0 * c1;
        q = m * (p - (3.0 / 2.0) * c1) - (27.0 / 2.0) * c0;
        sqrt_p = Math.sqrt(Math.abs(p));

        phi = 27.0 * (0.25 * SQR(c1) * (p - c1) + c0 * (q + 27.0 / 4.0 * c0));
        phi = (1.0 / 3.0) * Math.atan2(Math.sqrt(Math.abs(phi)), q);

        c = sqrt_p * Math.cos(phi);
        s = (1.0 / M_SQRT3) * sqrt_p * Math.sin(phi);

        w[1] = (1.0 / 3.0) * (m - c);
        w[2] = w[1] + s;
        w[0] = w[1] + c;
        w[1] -= s;

        return w;
    }

    // ----------------------------------------------------------------------------
    // Calculates the eigenvalues and normalized eigenvectors of a symmetric 3x3
    // matrix A using Cardano's method for the eigenvalues and an analytical
    // method based on vector cross products for the eigenvectors.
    // Only the diagonal and upper triangular parts of A need to contain meaningful
    // values. However, all of A may be used as temporary storage and may hence be
    // destroyed.
    // ----------------------------------------------------------------------------
    // Parameters:
    //   A: The symmetric input matrix
    //   Q: Storage buffer for eigenvectors
    //   w: Storage buffer for eigenvalues
    // ----------------------------------------------------------------------------
    // Return value:
    //   0: Success
    //  -1: Error
    // ----------------------------------------------------------------------------
    // Dependencies:
    //   dsyevc3()
    // ----------------------------------------------------------------------------
    // Version history:
    //   v1.1 (12 Mar 2012): Removed access to lower triangualr part of A
    //     (according to the documentation, only the upper triangular part needs
    //     to be filled)
    //   v1.0: First released version
    // ----------------------------------------------------------------------------
    double[][] dsyevv3(double[][] A, double[] w) {
        double[][] Q = new double[3][3];

        double norm;          // Squared norm or inverse norm of current eigenvector
        double n0, n1;        // Norm of first and second columns of A
        double n0tmp, n1tmp;  // "Templates" for the calculation of n0/n1 - saves a few FLOPS
        double thresh;        // Small number used as threshold for floating point comparisons
        double error;         // Estimated maximum roundoff error in some steps
        double wmax;          // The eigenvalue of maximum modulus
        double f, t;          // Intermediate storage
        int i, j;             // Loop counters

        wmax = Math.abs(w[0]);
        if ((t = Math.abs(w[1])) > wmax)
            wmax = t;
        if ((t = Math.abs(w[2])) > wmax)
            wmax = t;
        thresh = SQR(8.0 * DBL_EPSILON * wmax);

        // Prepare calculation of eigenvectors
        n0tmp = SQR(A[0][1]) + SQR(A[0][2]);
        n1tmp = SQR(A[0][1]) + SQR(A[1][2]);
        Q[0][1] = A[0][1] * A[1][2] - A[0][2] * A[1][1];
        Q[1][1] = A[0][2] * A[0][1] - A[1][2] * A[0][0];
        Q[2][1] = SQR(A[0][1]);

        // Calculate first eigenvector by the formula
        //   v[0] = (A - w[0]).e1 x (A - w[0]).e2
        A[0][0] -= w[0];
        A[1][1] -= w[0];
        Q[0][0] = Q[0][1] + A[0][2] * w[0];
        Q[1][0] = Q[1][1] + A[1][2] * w[0];
        Q[2][0] = A[0][0] * A[1][1] - Q[2][1];
        norm = SQR(Q[0][0]) + SQR(Q[1][0]) + SQR(Q[2][0]);
        n0 = n0tmp + SQR(A[0][0]);
        n1 = n1tmp + SQR(A[1][1]);
        error = n0 * n1;

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
        } else if (norm < SQR(64.0 * DBL_EPSILON) * error) {                         // If angle between A[0] and A[1] is too small, don't use
            t = SQR(A[0][1]);       // cross product, but calculate v ~ (1, -A0/A1, 0)
            f = -A[0][0] / A[0][1];
            if (SQR(A[1][1]) > t) {
                t = SQR(A[1][1]);
                f = -A[0][1] / A[1][1];
            }
            if (SQR(A[1][2]) > t)
                f = -A[0][2] / A[1][2];
            norm = 1.0 / Math.sqrt(1 + SQR(f));
            Q[0][0] = norm;
            Q[1][0] = f * norm;
            Q[2][0] = 0.0;
        } else                      // This is the standard branch
        {
            norm = Math.sqrt(1.0 / norm);
            for (j = 0; j < 3; j++)
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
            norm = SQR(Q[0][1]) + SQR(Q[1][1]) + SQR(Q[2][1]);
            n0 = n0tmp + SQR(A[0][0]);
            n1 = n1tmp + SQR(A[1][1]);
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
            } else if (norm < SQR(64.0 * DBL_EPSILON) * error) {                       // If angle between A[0] and A[1] is too small, don't use
                t = SQR(A[0][1]);     // cross product, but calculate v ~ (1, -A0/A1, 0)
                f = -A[0][0] / A[0][1];
                if (SQR(A[1][1]) > t) {
                    t = SQR(A[1][1]);
                    f = -A[0][1] / A[1][1];
                }
                if (SQR(A[1][2]) > t)
                    f = -A[0][2] / A[1][2];
                norm = 1.0 / Math.sqrt(1 + SQR(f));
                Q[0][1] = norm;
                Q[1][1] = f * norm;
                Q[2][1] = 0.0;
            } else {
                norm = Math.sqrt(1.0 / norm);
                for (j = 0; j < 3; j++)
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
            for (i = 0; i < 3; i++) {
                A[i][i] -= w[1];
                n0 = SQR(A[0][i]) + SQR(A[1][i]) + SQR(A[2][i]);
                if (n0 > thresh) {
                    Q[0][1] = Q[1][0] * A[2][i] - Q[2][0] * A[1][i];
                    Q[1][1] = Q[2][0] * A[0][i] - Q[0][0] * A[2][i];
                    Q[2][1] = Q[0][0] * A[1][i] - Q[1][0] * A[0][i];
                    norm = SQR(Q[0][1]) + SQR(Q[1][1]) + SQR(Q[2][1]);
                    if (norm > SQR(256.0 * DBL_EPSILON) * n0) // Accept cross product only if the angle between
                    {                                         // the two vectors was not too small
                        norm = Math.sqrt(1.0 / norm);
                        for (j = 0; j < 3; j++)
                            Q[j][1] = Q[j][1] * norm;
                        break;
                    }
                }
            }

            if (i == 3)    // This means that any vector orthogonal to v[0] is an EV.
            {
                for (j = 0; j < 3; j++)
                    if (Q[j][0] != 0.0)                                   // Find nonzero element of v[0] ...
                    {                                                     // ... and swap it with the next one
                        norm = 1.0 / Math.sqrt(SQR(Q[j][0]) + SQR(Q[(j + 1) % 3][0]));
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

        return Q;
    }
}
