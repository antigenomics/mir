package com.milaboratory.mir.structure.pdb.geometry.summary;

import com.milaboratory.mir.structure.pdb.Residue;
import com.milaboratory.mir.structure.pdb.geometry.Matrix3Symm;
import com.milaboratory.mir.structure.pdb.geometry.PointMass;
import com.milaboratory.mir.structure.pdb.geometry.Vector3;
import com.milaboratory.mir.thirdparty.AtomicDouble;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class InertiaTensor {
    private final Vector3 centerOfMass;
    private final Matrix3Symm inertiaTensorMatrix;

    public InertiaTensor(Iterable<Residue> residues) {
        this(StreamSupport
                .stream(residues.spliterator(), false)
                .flatMap(x -> x.getAtoms()
                        .stream()));
    }

    public InertiaTensor(Stream<PointMass> pointMassStream) {
        AtomicDouble xCmCounter = new AtomicDouble(), yCmCounter = new AtomicDouble(), zCmCounter = new AtomicDouble(),
                xxICounter = new AtomicDouble(), xyICounter = new AtomicDouble(), xzICounter = new AtomicDouble(),
                yyICounter = new AtomicDouble(), yzICounter = new AtomicDouble(),
                zzICounter = new AtomicDouble(),
                mTotCounter = new AtomicDouble();

        pointMassStream.forEach(pm -> {
            double mass = pm.getMass();

            if (mass > 0) {
                Vector3 coordinates = pm.getCoordinates();

                double x = coordinates.getX(),
                        y = coordinates.getY(),
                        z = coordinates.getZ();

                xCmCounter.addAndGet(x * mass);
                yCmCounter.addAndGet(y * mass);
                zCmCounter.addAndGet(z * mass);

                mTotCounter.addAndGet(mass);

                xxICounter.addAndGet(mass * (y * y + z * z));
                xyICounter.addAndGet(-mass * x * y);
                xzICounter.addAndGet(-mass * x * z);

                yyICounter.addAndGet(mass * (x * x + z * z));
                yzICounter.addAndGet(-mass * y * z);

                zzICounter.addAndGet(mass * (x * x + y * y));
            }
        });

        double mTot = mTotCounter.get(),
                xCm = xCmCounter.get() / mTot, yCm = yCmCounter.get() / mTot, zCm = zCmCounter.get() / mTot,

                xxI = xxICounter.get() - (yCm * yCm + zCm * zCm) * mTot,
                xyI = xyICounter.get() + (xCm * yCm) * mTot,
                xzI = xzICounter.get() + (xCm * zCm) * mTot,

                yyI = yyICounter.get() - (xCm * xCm + zCm * zCm) * mTot,
                yzI = yzICounter.get() + (yCm * zCm) * mTot,

                zzI = zzICounter.get() - (xCm * xCm + yCm * yCm) * mTot;

        this.centerOfMass = new Vector3(xCm, yCm, zCm);
        this.inertiaTensorMatrix = new Matrix3Symm(xxI, xyI, xzI,
                yyI, yzI,
                zzI);
    }

    public Vector3 getCenterOfMass() {
        return centerOfMass;
    }

    public Matrix3Symm getInertiaTensorMatrix() {
        return inertiaTensorMatrix;
    }

    @Override
    public String toString() {
        return "CM=" + centerOfMass.toString() + "\n" +
                "I=" + inertiaTensorMatrix.toString();
    }
}
