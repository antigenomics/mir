package com.milaboratory.mir.structure.output;

import com.milaboratory.mir.TableWriter;
import com.milaboratory.mir.structure.pdb.geometry.summary.ChainTorsionAngles;
import com.milaboratory.mir.structure.pdb.geometry.Vector3;
import com.milaboratory.mir.structure.pdb.geometry.summary.ResidueTorsionAngles;
import com.milaboratory.mir.structure.pdb.geometry.summary.StructureAxesAndTorsions;

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
        return chainTorsions.getTorsionAngleList().stream()
                .map(x -> {
                    var res = x.getCurrent().getResidue();
                    return prefix + "\t" +
                            res.getSequentialResidueSequenceNumber() + "\t" +
                            res.getResidueSequenceNumber() + "\t" +
                            res.getResidueInsertionCode() + "\t" +
                            res.getResidueName().getLetter() + "\t" +
                            writeBackboneRow(x);
                })
                .collect(Collectors.joining("\n"));
    }

    protected String writeBackboneRow(ResidueTorsionAngles residueTorsionAngles) {
        var ca = residueTorsionAngles.getCurrent().getCA();
        return (ca == null ? new Vector3(Double.NaN, Double.NaN, Double.NaN) : ca.getCoordinates()).toRow();
    }
}
