package com.milaboratory.mir.structure;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.mhc.MhcRegionType;
import com.milaboratory.mir.segment.ConstantSegment;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;
import com.milaboratory.mir.structure.pdb.Chain;

public class AntigenReceptorChain implements StructureChainWithMarkup<AntigenReceptorRegionType> {
    private final SequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType, ? extends SequenceRegionMarkup> markup;
    private final AntigenReceptorChainType antigenReceptorChainType;
    private final VariableSegment variableSegment;
    private final JoiningSegment joiningSegment;
    private final ConstantSegment constantSegment;
    private final Chain structureChain;

    public AntigenReceptorChain(SequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType, ? extends SequenceRegionMarkup> markup,
                                AntigenReceptorChainType antigenReceptorChainType,
                                VariableSegment variableSegment,
                                JoiningSegment joiningSegment,
                                ConstantSegment constantSegment,
                                Chain structureChain) {
        this.markup = markup;
        this.antigenReceptorChainType = antigenReceptorChainType;
        this.variableSegment = variableSegment;
        this.joiningSegment = joiningSegment;
        this.constantSegment = constantSegment;
        this.structureChain = structureChain;
    }

    @Override
    public SequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType, ? extends SequenceRegionMarkup> getMarkup() {
        return markup;
    }

    public AntigenReceptorChainType getAntigenReceptorChainType() {
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
