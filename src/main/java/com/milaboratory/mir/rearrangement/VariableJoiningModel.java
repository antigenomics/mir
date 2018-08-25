package com.milaboratory.mir.rearrangement;

import com.milaboratory.mir.rearrangement.blocks.*;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

public class VariableJoiningModel implements RearrangementModel {
    private final VariableDistribution variableSegmentDistribution;
    private final JoiningVariableSegmentDistribution joiningVariableSegmentDistribution;
    private final JoiningSegmentTrimmingDistribution joiningSegmentTrimmingDistribution;
    private final VariableSegmentTrimmingDistribution variableSegmentTrimmingDistribution;
    private final InsertSizeDistribution insertSizeDistribution;
    private final NucleotideDistribution nucleotideDistribution;
    private final NucleotidePairDistribution nucleotidePairDistribution;
    private final MarkovNucleotideSequenceDistribution markovSequenceDistribution;

    public VariableJoiningModel(VariableDistribution variableSegmentDistribution,
                                JoiningVariableSegmentDistribution joiningVariableSegmentDistribution,
                                JoiningSegmentTrimmingDistribution joiningSegmentTrimmingDistribution,
                                VariableSegmentTrimmingDistribution variableSegmentTrimmingDistribution,
                                InsertSizeDistribution insertSizeDistribution,
                                NucleotideDistribution nucleotideDistribution,
                                NucleotidePairDistribution nucleotidePairDistribution) {
        this.variableSegmentDistribution = variableSegmentDistribution;
        this.joiningVariableSegmentDistribution = joiningVariableSegmentDistribution;
        this.joiningSegmentTrimmingDistribution = joiningSegmentTrimmingDistribution;
        this.variableSegmentTrimmingDistribution = variableSegmentTrimmingDistribution;
        this.insertSizeDistribution = insertSizeDistribution;
        this.nucleotideDistribution = nucleotideDistribution;
        this.nucleotidePairDistribution = nucleotidePairDistribution;
        this.markovSequenceDistribution = new MarkovNucleotideSequenceDistribution(nucleotideDistribution,
                nucleotidePairDistribution, false);
    }

    @Override
    public boolean hasD() {
        return false;
    }

    @Override
    public RearrangementTemplate generate() {
        VariableSegment variableSegment = variableSegmentDistribution.getDistributionSampler().generate();
        JoiningSegment joiningSegment = joiningVariableSegmentDistribution.getDistribution0(variableSegment)
                .getDistributionSampler().generate();

        int variableTrimming = variableSegmentTrimmingDistribution.getDistribution0(variableSegment)
                .getDistributionSampler().generate();
        int joiningTrimming = joiningSegmentTrimmingDistribution.getDistribution0(joiningSegment)
                .getDistributionSampler().generate();

        int insertSize = insertSizeDistribution.getDistributionSampler().generate();

        return new RearrangementTemplate(
                variableSegment,
                joiningSegment,
                variableTrimming,
                joiningTrimming,
                markovSequenceDistribution.generate(insertSize)
        );
    }

    public VariableDistribution getVariableSegmentDistribution() {
        return variableSegmentDistribution;
    }

    public JoiningVariableSegmentDistribution getJoiningVariableSegmentDistribution() {
        return joiningVariableSegmentDistribution;
    }

    public JoiningSegmentTrimmingDistribution getJoiningSegmentTrimmingDistribution() {
        return joiningSegmentTrimmingDistribution;
    }

    public VariableSegmentTrimmingDistribution getVariableSegmentTrimmingDistribution() {
        return variableSegmentTrimmingDistribution;
    }

    public InsertSizeDistribution getInsertSizeDistribution() {
        return insertSizeDistribution;
    }

    public NucleotideDistribution getNucleotideDistribution() {
        return nucleotideDistribution;
    }

    public NucleotidePairDistribution getNucleotidePairDistribution() {
        return nucleotidePairDistribution;
    }
}
