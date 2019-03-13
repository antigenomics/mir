package com.antigenomics.mir.structure.output;

import com.antigenomics.mir.TableWriter;
import com.antigenomics.mir.structure.pdb.contacts.ChainRegionPairwiseDistances;
import com.antigenomics.mir.structure.pdb.contacts.TcrPeptideMhcContactMap;
import com.antigenomics.mir.structure.AntigenReceptorRegionType;
import com.antigenomics.mir.structure.StructureChainWithMarkup;
import com.antigenomics.mir.structure.pdb.contacts.ResiduePairAtomDistances;
import com.antigenomics.mir.structure.pdb.contacts.TcrChainContacts;

import java.io.OutputStream;
import java.util.stream.Collectors;

public class TcrPeptideMhcContactMapWriter extends TableWriter<TcrPeptideMhcContactMap> {
    private static final String HEADER =
            "pdb.id\tspecies\tmhc.class\t" +
                    "tcr.gene\ttarget.type\t" +
                    "tcr.allele\ttarget.allele\t" +
                    "tcr.region\ttarget.region\t" +
                    "tcr.region.start\ttarget.region.start\t" +
                    "tcr.region.len\ttarget.region.len\t" +
                    "tcr.pos.abs\ttarget.pos.abs\t" +
                    "tcr.aa\ttarget.aa\tdist.CA\t" +
                    "tcr.atom\ttarget.atom\tdist.min";

    public TcrPeptideMhcContactMapWriter(OutputStream os) {
        super(os, HEADER);
    }

    protected TcrPeptideMhcContactMapWriter(OutputStream os, String header) {
        super(os, header);
    }

    @Override
    protected String convert(TcrPeptideMhcContactMap contacts) {
        var tcrPeptideMhcComplex = contacts.getTcrPeptideMhcComplex();

        // pdb.id species mhc.class
        String prefix1 = tcrPeptideMhcComplex.getStructure().getId() + "\t" +
                tcrPeptideMhcComplex.getSpecies() + "\t" +
                tcrPeptideMhcComplex.getPeptideMhcComplex().getMhcComplex().getMhcClassType();

        return contacts
                .getTcrChainContactsList()
                .stream()
                .map(x -> convert(prefix1, x))
                .filter(x -> !x.isEmpty())
                .collect(Collectors.joining("\n"));
    }

    private <E extends Enum<E>, C extends StructureChainWithMarkup<E>>
    String convert(String prefix1, TcrChainContacts<E, C> contacts) {
        var tcrChain = contacts.getTcrChain();
        var otherChain = contacts.getOtherChain();

        // tcr.gene target.type
        // tcr.allele target.allele

        String prefix2 = prefix1 + "\t" +
                tcrChain.getChainTypeStr() + "\t" + otherChain.getChainTypeStr() + "\t" +
                tcrChain.getAlleleInfoStr() + "\t" + otherChain.getAlleleInfoStr();

        return contacts
                .getContactsByRegion()
                .stream()
                .map(x -> convert(prefix2, x))
                .filter(x -> !x.isEmpty())
                .collect(Collectors.joining("\n"));
    }

    private <E extends Enum<E>>
    String convert(String prefix2, ChainRegionPairwiseDistances<AntigenReceptorRegionType, E> contacts) {
        var sequenceRegion1 = contacts.getSequenceRegion1();
        var sequenceRegion2 = contacts.getSequenceRegion2();

        // tcr.region target.region
        // tcr.region.start target.region.start
        // tcr.region.len target.region.len

        String prefix3 = prefix2 + "\t" +
                sequenceRegion1.getRegionType() + "\t" + sequenceRegion2.getRegionType() + "\t" +
                sequenceRegion1.getStart() + "\t" + sequenceRegion2.getEnd() + "\t" +
                sequenceRegion1.getSize() + "\t" + sequenceRegion2.getSize();

        return contacts
                .getResiduePairAtomDistances()
                .stream()
                .map(x -> convert(prefix3, x))
                .filter(x -> !x.isEmpty())
                .collect(Collectors.joining("\n"));
    }

    private String convert(String prefix3, ResiduePairAtomDistances dist) {
        var res1 = dist.getResidue1();
        var res2 = dist.getResidue2();

        // tcr.pos.abs target.pos.abs
        // tcr.aa	target.aa	dist.CA
        // tcr.atom	target.atom	dist.min

        return prefix3 + "\t" +
                res1.getSequentialResidueSequenceNumber() + "\t" + res2.getSequentialResidueSequenceNumber() + "\t" +
                res1.getResidueName().getLetter() + "\t" + res2.getResidueName().getLetter() + "\t" + dist.getCaDistance() + "\t" +
                dist.getClosestAtom1().getAtomName().getLetter() + "\t" + dist.getClosestAtom2().getAtomName().getLetter() + "\t" + dist.getMinAtomDistance();
    }

}
