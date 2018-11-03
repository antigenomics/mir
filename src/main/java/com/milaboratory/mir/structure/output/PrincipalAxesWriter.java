package com.milaboratory.mir.structure.output;

import com.milaboratory.mir.TableWriter;
import com.milaboratory.mir.structure.pdb.geometry.ChainPrincipalAxes;
import com.milaboratory.mir.structure.pdb.geometry.StructureAxesAndTorsions;

import java.io.OutputStream;
import java.util.stream.Collectors;

public class PrincipalAxesWriter extends TableWriter<StructureAxesAndTorsions> {
    private static final String HEADER =
            "pdb.id\tchain.id\tvector.type\tx\ty\tz";

    public PrincipalAxesWriter(OutputStream os) {
        super(os, HEADER);
    }

    @Override
    protected String convert(StructureAxesAndTorsions obj) {
        return obj.getPrincipalAxesByChain().values().stream()
                .map(x -> writeChain(
                        obj.getStructure().getId() + "\t" + x.getChain().getChainIdentifier(),
                        x))
                .collect(Collectors.joining("\n"));
    }

    private String writeChain(String prefix, ChainPrincipalAxes cpr) {
        return prefix + "\tCM\t" + cpr.getCenterOfMass().toRow() + "\n" +
                prefix + "\tPR1\t" + cpr.getPrincipalAxis1().toRow() + "\n" +
                prefix + "\tPR2\t" + cpr.getPrincipalAxis2().toRow() + "\n" +
                prefix + "\tPR3\t" + cpr.getPrincipalAxis3().toRow();
    }
}
