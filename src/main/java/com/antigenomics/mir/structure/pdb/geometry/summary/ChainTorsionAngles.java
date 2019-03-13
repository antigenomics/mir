package com.antigenomics.mir.structure.pdb.geometry.summary;

import com.antigenomics.mir.structure.pdb.Chain;
import com.antigenomics.mir.structure.pdb.Residue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ChainTorsionAngles {
    private final Chain chain;
    private final List<ResidueTorsionAngles> torsionAngleList;

    public ChainTorsionAngles(Chain chain) {
        this.torsionAngleList = new ArrayList<>(chain.getResidues().size());

        ResidueBackbone bbPrevPrev = null, bbPrev = null;

        for (Residue res : chain) {
            var bb = new ResidueBackbone(res);

            if (bbPrevPrev != null) {
                torsionAngleList.add(new ResidueTorsionAngles(bbPrevPrev, bbPrev, bb));
            }

            // shift
            bbPrevPrev = bbPrev;
            bbPrev = bb;
        }
        this.chain = chain;
    }

    public Chain getChain() {
        return chain;
    }

    public List<ResidueTorsionAngles> getTorsionAngleList() {
        return Collections.unmodifiableList(torsionAngleList);
    }
}
