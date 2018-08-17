package com.milaboratory.mir.model.generator;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.segment.DiversitySegment;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

public class VDJRearrangementGenerator implements RearrangmentGenerator<VDJRearrangementInfo> {
    private final SegmentGenerator<VariableSegment> vSegmentGenerator;
    private final SegmentGenerator<JoiningSegment> jSegmentGenerator;
    private final ConditionalSegmentGenerator<DiversitySegment, JoiningSegment> djSegmentGenerator;
    private final SegmentTrimmingGenerator<VariableSegment> vSegmentTrimmingGenerator;
    private final SegmentTrimmingGenerator<JoiningSegment> jSegmentTrimmingGenerator;
    private final SegmentTrimming2Generator<DiversitySegment> dSegmentTrimmingGenerator;
    private final InsertSizeGenerator vdInsertSizeGenerator, djInsertSizeGenerator;
    private final SequenceGenerator<NucleotideSequence> vdInsertGenerator, djInsertGenerator;


    public VDJRearrangementGenerator(SegmentGenerator<VariableSegment> vSegmentGenerator,
                                     SegmentGenerator<JoiningSegment> jSegmentGenerator,
                                     ConditionalSegmentGenerator<DiversitySegment, JoiningSegment> djSegmentGenerator,
                                     SegmentTrimmingGenerator<VariableSegment> vSegmentTrimmingGenerator,
                                     SegmentTrimmingGenerator<JoiningSegment> jSegmentTrimmingGenerator,
                                     SegmentTrimming2Generator<DiversitySegment> dSegmentTrimmingGenerator,
                                     InsertSizeGenerator vdInsertSizeGenerator,
                                     InsertSizeGenerator djInsertSizeGenerator,
                                     SequenceGenerator<NucleotideSequence> vdInsertGenerator,
                                     SequenceGenerator<NucleotideSequence> djInsertGenerator) {
        this.vSegmentGenerator = vSegmentGenerator;
        this.jSegmentGenerator = jSegmentGenerator;
        this.djSegmentGenerator = djSegmentGenerator;
        this.vSegmentTrimmingGenerator = vSegmentTrimmingGenerator;
        this.jSegmentTrimmingGenerator = jSegmentTrimmingGenerator;
        this.dSegmentTrimmingGenerator = dSegmentTrimmingGenerator;
        this.vdInsertSizeGenerator = vdInsertSizeGenerator;
        this.djInsertSizeGenerator = djInsertSizeGenerator;
        this.vdInsertGenerator = vdInsertGenerator;
        this.djInsertGenerator = djInsertGenerator;
    }

    @Override
    public VDJRearrangementInfo generate() {
        VariableSegment vSegment = vSegmentGenerator.generate();
        JoiningSegment jSegment = jSegmentGenerator.generate();
        DiversitySegment dSegment = djSegmentGenerator.generate(jSegment);
        var dTrimming = dSegmentTrimmingGenerator.generate(dSegment);
        int vTrimming = vSegmentTrimmingGenerator.generate(vSegment),
                jTrimming = jSegmentTrimmingGenerator.generate(jSegment),
                dTrimming5 = dTrimming.getFivePrime(),
                dTrimming3 = dTrimming.getThreePrime(),
                vdInsertSize = vdInsertSizeGenerator.generate(),
                djInsertSize = djInsertSizeGenerator.generate();

        NucleotideSequence vdInsert = vdInsertGenerator.generateForward(vdInsertSize),
                djInsert = djInsertGenerator.generateForward(djInsertSize);

        return new VDJRearrangementInfo(vSegment, jSegment, dSegment,
                vTrimming, jTrimming, dTrimming5, dTrimming3,
                vdInsertSize, djInsertSize,
                vdInsert, djInsert);
    }
}
