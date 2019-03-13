package com.antigenomics.mir.structure.pdb.contacts;

import com.antigenomics.mir.structure.*;
import com.antigenomics.mir.structure.*;
import com.antigenomics.mir.structure.pdb.Structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class TcrPeptideMhcContactMap {
    private final TcrPeptideMhcComplex tcrPeptideMhcComplex;
    private final List<TcrChainContacts> tcrChainContactsList = new ArrayList<>();

    public TcrPeptideMhcContactMap(AntigenReceptor tcr, PeptideMhcComplex pMhc,
                                   String structureId) {
        this(tcr, pMhc, structureId, 25f, 5f, true, true);
    }

    public TcrPeptideMhcContactMap(TcrPeptideMhcComplex tcrPart, TcrPeptideMhcComplex pMhcPart) {
        this(tcrPart, pMhcPart, 25f, 5f, true);
    }

    public TcrPeptideMhcContactMap(TcrPeptideMhcComplex tcrPart, TcrPeptideMhcComplex pMhcPart,
                                   float maxCaDistance, float maxAtomDistance,
                                   boolean allCdrPeptide) {
        this(tcrPart.getAntigenReceptor(),
                pMhcPart.getPeptideMhcComplex(),
                tcrPart.getStructure().getId() + ":" + pMhcPart.getStructure().getId(),
                maxCaDistance, maxAtomDistance, allCdrPeptide, true
        );
    }

    public TcrPeptideMhcContactMap(AntigenReceptor tcr, PeptideMhcComplex pMhc,
                                   String structureId,
                                   float maxCaDistance, float maxAtomDistance,
                                   boolean allCdrPeptide,
                                   boolean fixChainNameCollision) {
        this(new TcrPeptideMhcComplex(tcr, pMhc,
                        new Structure(structureId,
                                fixChainNameCollision ?
                                        Arrays.asList(tcr.getFirstChain().getStructureChain().withIdentifier('A'),
                                                tcr.getSecondChain().getStructureChain().withIdentifier('B'),
                                                pMhc.getPeptideChain().getStructureChain().withIdentifier('C'),
                                                pMhc.getMhcComplex().getFirstChain().getStructureChain().withIdentifier('D'),
                                                pMhc.getMhcComplex().getSecondChain().getStructureChain().withIdentifier('E')
                                        ) :
                                        Arrays.asList(tcr.getFirstChain().getStructureChain(),
                                                tcr.getSecondChain().getStructureChain(),
                                                pMhc.getPeptideChain().getStructureChain(),
                                                pMhc.getMhcComplex().getFirstChain().getStructureChain(),
                                                pMhc.getMhcComplex().getSecondChain().getStructureChain()
                                        ))
                ),
                maxCaDistance, maxAtomDistance, allCdrPeptide);
    }


    public TcrPeptideMhcContactMap(TcrPeptideMhcComplex tcrPeptideMhcComplex) {
        this(tcrPeptideMhcComplex, 25f, 5f, true);
    }

    public TcrPeptideMhcContactMap(TcrPeptideMhcComplex tcrPeptideMhcComplex,
                                   float maxCaDistance, float maxAtomDistance,
                                   boolean allCdrPeptide) {
        this.tcrPeptideMhcComplex = tcrPeptideMhcComplex;
        var tcr = tcrPeptideMhcComplex.getAntigenReceptor();
        var pMhc = tcrPeptideMhcComplex.getPeptideMhcComplex();
        for (AntigenReceptorChain tcrChain : Arrays.asList(tcr.getFirstChain(), tcr.getSecondChain())) {
            for (StructureChainWithMarkup otherChain : Arrays.asList(pMhc.getPeptideChain(),
                    pMhc.getMhcComplex().getFirstChain(), pMhc.getMhcComplex().getSecondChain())) {
                tcrChainContactsList.add(new TcrChainContacts<>(tcrChain, otherChain,
                        maxCaDistance, maxAtomDistance, allCdrPeptide));
            }
        }
    }

    public TcrPeptideMhcComplex getTcrPeptideMhcComplex() {
        return tcrPeptideMhcComplex;
    }

    public List<TcrChainContacts> getTcrChainContactsList() {
        return Collections.unmodifiableList(tcrChainContactsList);
    }
}
