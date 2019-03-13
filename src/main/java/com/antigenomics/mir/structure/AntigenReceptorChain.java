package com.antigenomics.mir.structure;

import com.antigenomics.mir.mappers.markup.SequenceRegionMarkup;
import com.antigenomics.mir.segment.ConstantSegment;
import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.segment.JoiningSegment;
import com.antigenomics.mir.segment.VariableSegment;
import com.antigenomics.mir.structure.pdb.Chain;
import com.milaboratory.core.sequence.AminoAcidSequence;

public final class AntigenReceptorChain implements StructureChainWithMarkup<AntigenReceptorRegionType> {
    private final SequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType, ? extends SequenceRegionMarkup> markup;
    private final VariableSegment variableSegment;
    private final JoiningSegment joiningSegment;
    private final ConstantSegment constantSegment;
    private final Chain structureChain;
    private final Gene gene;

    public AntigenReceptorChain(SequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType, ? extends SequenceRegionMarkup> markup,
                                VariableSegment variableSegment,
                                JoiningSegment joiningSegment,
                                ConstantSegment constantSegment,
                                Chain structureChain) {
        this.markup = markup;
        this.variableSegment = variableSegment;
        this.joiningSegment = joiningSegment;
        this.constantSegment = constantSegment;
        this.structureChain = structureChain;
        // todo: better impl for specifying gene
        this.gene = Gene.valueOf(variableSegment.getId().substring(0, 3));
    }

    @Override
    public SequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType, ? extends SequenceRegionMarkup> getMarkup() {
        return markup;
    }

    public Gene getGene() {
        return gene;
    }

    public VariableSegment getVariableSegment() {
        return variableSegment;
    }

    public JoiningSegment getJoiningSegment() {
        return joiningSegment;
    }

    public ConstantSegment getConstantSegment() {
        return constantSegment;
    }

    @Override
    public Chain getStructureChain() {
        return structureChain;
    }

    @Override
    public String getChainTypeStr() {
        return gene.toString();
    }

    @Override
    public String getAlleleInfoStr() {
        return variableSegment.getId() + ":" + joiningSegment.getId();
    }
}