package com.milaboratory.mir.rearrangement;

import com.milaboratory.mir.rearrangement.blocks.*;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

public class VariableJoiningModel implements RearrangementModel {
    private final VariableDistribution variableSegmentDistribution;
    private final JoiningGivenVariableSegmentDistribution joiningGivenVariableSegmentDistribution;
    private final JoiningSegmentTrimmingDistribution joiningSegmentTrimmingDistribution;
    private final VariableSegmentTrimmingDistribution variableSegmentTrimmingDistribution;
    private final InsertSizeDistribution insertSizeDistribution;
    private final NucleotideDistribution nucleotideDistribution;
    private final NucleotidePairDistribution nucleotidePairDistribution;
    private final MarkovNucleotideSequenceDistribution markovSequenceDistribution;

    public VariableJoiningModel(VariableDistribution variableSegmentDistribution,
                                JoiningGivenVariableSegmentDistribution joiningGivenVariableSegmentDistribution,
                                JoiningSegmentTrimmingDistribution joiningSegmentTrimmingDistribution,
                                VariableSegmentTrimmingDistribution variableSegmentTrimmingDistribution,
                                InsertSizeDistribution insertSizeDistribution,
                                NucleotideDistribution nucleotideDistribution,
                                NucleotidePairDistribution nucleotidePairDistribution) {
        this.variableSegmentDistribution = variableSegmentDistribution;
        this.joiningGivenVariableSegmentDistribution = joiningGivenVariableSegmentDistribution;
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
        JoiningSegment joiningSegment = joiningGivenVariableSegmentDistribution.getDistribution0(variableSegment)
                .getDistributionSampler().generate();
        int variableTrimming = variableSegmentTrimmingDistribution.getDistribution0(variableSegment)
                .getDistributionSampler().generate();
        int joiningTrimming = joiningSegmentTrimmingDistribution.getDistribution0(joiningSegment)
                .getDistributionSampler().generate();
        Integer insertSize = insertSizeDistribution.getDistributionSampler().generate();
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

    public JoiningGivenVariableSegmentDistribution getJoiningGivenVariableSegmentDistribution() {
        return joiningGivenVariableSegmentDistribution;
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
