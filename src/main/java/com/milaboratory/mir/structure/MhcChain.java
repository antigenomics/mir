package com.milaboratory.mir.structure;

import com.milaboratory.mir.mhc.MhcAllele;
import com.milaboratory.mir.mhc.MhcChainType;
import com.milaboratory.mir.structure.pdb.Chain;

public interface MhcChain {
    MhcAllele getMhcAllele();

    MhcChainType getMhcChainType();

    Chain getChain();
}
