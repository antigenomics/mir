package com.milaboratory.mir.structure;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.mhc.MhcRegionType;
import com.milaboratory.mir.segment.ConstantSegment;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;
import com.milaboratory.mir.structure.pdb.Chain;

public class AntigenReceptorChain implements StructureChainWithMarkup<AntigenReceptorRegionType> {
    private final SequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType, ? extends SequenceRegionMarkup> markup;
    private final VariableSegment variableSegment;
    private final JoiningSegment joiningSegment;
    private final ConstantSegment constantSegment;
    private final Chain structureChain;
    private final Gene antigenReceptorChainType;

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
        this.antigenReceptorChainType = Gene.valueOf(variableSegment.getId().substring(0, 3));
    }

    @Override
    public SequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType, ? extends SequenceRegionMarkup> getMarkup() {
        return markup;
    }

    public Gene getAntigenReceptorChainType() {
        return antigenReceptorChainType;
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
}
