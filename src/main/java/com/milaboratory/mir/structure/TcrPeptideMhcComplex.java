package com.milaboratory.mir.structure;

import com.milaboratory.mir.structure.pdb.Structure;

public interface TcrPeptideMhcComplex {
    AntigenReceptor getAntigenReceptor();

    PeptideMhcComplex getPeptideMhcComplex();

    Structure getStructure();
}
