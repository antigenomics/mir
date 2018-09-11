package com.milaboratory.mir.complex;

public interface PeptideMhcComplex {
    Peptide getPeptide();

    MhcChain getFirstChain();

    MhcChain getSecondChain();
}
