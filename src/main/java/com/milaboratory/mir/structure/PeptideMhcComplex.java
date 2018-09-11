package com.milaboratory.mir.structure;

public interface PeptideMhcComplex {
    Peptide getPeptide();

    MhcChain getFirstChain();

    MhcChain getSecondChain();
}
