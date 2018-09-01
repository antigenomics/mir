package com.milaboratory.mir.rearrangement;

import com.milaboratory.mir.rearrangement.blocks.*;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

public class VariableJoiningModel implements RearrangementModel<VariableJoiningModel> {
    private final VariableDistribution variableSegmentDistribution;
    private final JoiningVariableDistribution joiningVariableDistribution;
    private final JoiningTrimmingDistribution joiningTrimmingDistribution;
    private final VariableTrimmingDistribution variableTrimmingDistribution;
    private final InsertSizeDistribution insertSizeDistribution;
    private final NucleotideDistribution nucleotideDistribution;
    private final NucleotidePairDistribution nucleotidePairDistribution;
    private final MarkovNucleotideSequenceDistribution markovSequenceDistribution;

    public VariableJoiningModel(VariableDistribution variableSegmentDistribution,
                                JoiningVariableDistribution joiningVariableDistribution,
                                JoiningTrimmingDistribution joiningTrimmingDistribution,
                                VariableTrimmingDistribution variableTrimmingDistribution,
                                InsertSizeDistribution insertSizeDistribution,
                                NucleotideDistribution nucleotideDistribution,
                                NucleotidePairDistribution nucleotidePairDistribution) {
        this.variableSegmentDistribution = variableSegmentDistribution;
        this.joiningVariableDistribution = joiningVariableDistribution;
        this.joiningTrimmingDistribution = joiningTrimmingDistribution;
        this.variableTrimmingDistribution = variableTrimmingDistribution;
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
        JoiningSegment joiningSegment = joiningVariableDistribution.getDistribution0(variableSegment)
                .getDistributionSampler().generate();

        int variableTrimming = variableTrimmingDistribution.getDistribution0(variableSegment)
                .getDistributionSampler().generate();
        int joiningTrimming = joiningTrimmingDistribution.getDistribution0(joiningSegment)
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

    @Override
    public VariableJoiningModel getUpdatedModel() {
        return new VariableJoiningModel(
                variableSegmentDistribution.copy(true),
                joiningVariableDistribution.copy(true),
                joiningTrimmingDistribution.copy(true),
                variableTrimmingDistribution.copy(true),
                insertSizeDistribution.copy(true),
                nucleotideDistribution.copy(true),
                nucleotidePairDistribution.copy(true)
        );
    }

    public VariableJoiningModel withSegmentDistribution(VariableDistribution variableSegmentDistribution,
                                                        JoiningVariableDistribution joiningVariableDistribution) {
        // todo: compatibility test
        return new VariableJoiningModel(
                variableSegmentDistribution,
                joiningVariableDistribution,
                joiningTrimmingDistribution.copy(),
                variableTrimmingDistribution.copy(),
                insertSizeDistribution.copy(),
                nucleotideDistribution.copy(),
                nucleotidePairDistribution.copy()
        );
    }

    public VariableDistribution getVariableSegmentDistribution() {
        return variableSegmentDistribution;
    }

    public JoiningVariableDistribution getJoiningVariableDistribution() {
        return joiningVariableDistribution;
    }

    public JoiningTrimmingDistribution getJoiningTrimmingDistribution() {
        return joiningTrimmingDistribution;
    }

    public VariableTrimmingDistribution getVariableTrimmingDistribution() {
        return variableTrimmingDistribution;
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
