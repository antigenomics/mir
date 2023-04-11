package com.antigenomics.mir.structure.output;

import com.antigenomics.mir.TableWriter;
import com.antigenomics.mir.structure.pdb.Residue;
import com.antigenomics.mir.structure.pdb.geometry.summary.ChainTorsionAngles;
import com.antigenomics.mir.structure.pdb.geometry.Vector3;
import com.antigenomics.mir.structure.pdb.geometry.summary.ResidueBackbone;
import com.antigenomics.mir.structure.pdb.geometry.summary.ResidueTorsionAngles;
import com.antigenomics.mir.structure.pdb.geometry.summary.StructureAxesAndTorsions;

import java.io.OutputStream;
import java.util.stream.Collectors;

public class BackboneWriter extends TableWriter<StructureAxesAndTorsions> {
    private static final String HEADER =
            "pdb.id\tchain.id\tresidue.index\tresidue.index.pdb\tresidue.ins.code\tresidue.aa\tx\ty\tz";

    public BackboneWriter(OutputStream os) {
        super(os, HEADER);
    }

    protected BackboneWriter(OutputStream os, String header) {
        super(os, header);
    }

    @Override
    protected String convert(StructureAxesAndTorsions obj) {
        return obj.getTorsionAnglesByChain().values().stream()
                .map(x -> writeChain(
                        obj.getStructure().getId() + "\t" + x.getChain().getChainIdentifier(),
                        x))
                .collect(Collectors.joining("\n"));
    }

    private String writeChain(String prefix, ChainTorsionAngles chainTorsions) {
        return chainTorsions.getChain().getResidues().stream()
                .map(res -> prefix + "\t" +
                        res.getSequentialResidueSequenceNumber() + "\t" +
                        res.getResidueSequenceNumber() + "\t" +
                        res.getResidueInsertionCode() + "\t" +
                        res.getResidueName().getLetter() + "\t" +
                        writeBackboneRow(res))
                .collect(Collectors.joining("\n"));
    }

    protected String writeBackboneRow(Residue residue) {
        var ca = new ResidueBackbone(residue).getCA();
        return (ca == null ? new Vector3(Double.NaN, Double.NaN, Double.NaN) : ca.getCoordinates()).toRow();
    }

    protected String writeBackboneRow(ResidueTorsionAngles residueTorsionAngles) {
        var ca = residueTorsionAngles.getCurrent().getCA();
        return (ca == null ? new Vector3(Double.NaN, Double.NaN, Double.NaN) : ca.getCoordinates()).toRow();
    }
}
