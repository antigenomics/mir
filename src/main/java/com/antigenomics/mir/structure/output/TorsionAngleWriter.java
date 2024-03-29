package com.antigenomics.mir.structure.output;

import com.antigenomics.mir.structure.pdb.geometry.summary.ResidueTorsionAngles;

import java.io.OutputStream;

public class TorsionAngleWriter extends BackboneWriter {
    private static final String HEADER =
            "pdb.id\tchain.id\tresidue.index\tresidue.index.pdb\tresidue.ins.code\tresidue.aa\tphi\tpsi\tomega";

    public TorsionAngleWriter(OutputStream os) {
        super(os, HEADER);
    }

    @Override
    protected String writeBackboneRow(ResidueTorsionAngles residueTorsionAngles) {
        return residueTorsionAngles.asRow(); /*+ "\t" +
                residueTorsionAngles.getPrevious().getResidue().getResidueName().getLetter() +
                residueTorsionAngles.getCurrent().getResidue().getResidueName().getLetter() +
                residueTorsionAngles.getNext().getResidue().getResidueName().getLetter();*/
    }
}
