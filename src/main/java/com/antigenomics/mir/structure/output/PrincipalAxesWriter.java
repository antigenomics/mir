package com.antigenomics.mir.structure.output;

import com.antigenomics.mir.TableWriter;
import com.antigenomics.mir.structure.pdb.geometry.summary.ChainPrincipalAxes;
import com.antigenomics.mir.structure.pdb.geometry.summary.StructureAxesAndTorsions;

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
        return prefix + "\tCM\t" + cpr.getPrincipalAxes().getInertiaTensor().getCenterOfMass().toRow() + "\n" +
                prefix + "\tPR1\t" + cpr.getPrincipalAxes().getPrincipalAxis1().toRow() + "\n" +
                prefix + "\tPR2\t" + cpr.getPrincipalAxes().getPrincipalAxis2().toRow() + "\n" +
                prefix + "\tPR3\t" + cpr.getPrincipalAxes().getPrincipalAxis3().toRow() + "\n" +
                prefix + "\tI\t" + cpr.getPrincipalAxes().getPrincipalMoments().toRow();
    }
}
