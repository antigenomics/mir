package com.milaboratory.mir.model.gen;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.segment.SegmentId;

public class VDJRearrangementGenerator implements RearrangmentGenerator<VDJRearrangementInfo> {
    private final SegmentGenerator vSegmentGenerator, jSegmentGenerator;
    private final ConditionalSegmentGenerator djSegmentGenerator;
    private final SegmentTrimmingGenerator vSegmentTrimmingGenerator, jSegmentTrimmingGenerator;
    private final SegmentTrimming2Generator dSegmentTrimmingGenerator;
    private final InsertSizeGenerator vdInsertSizeGenerator, djInsertSizeGenerator;
    private final SequenceGenerator<NucleotideSequence> vdInsertGenerator, djInsertGenerator;


    public VDJRearrangementGenerator(SegmentGenerator vSegmentGenerator,
                                     SegmentGenerator jSegmentGenerator,
                                     ConditionalSegmentGenerator djSegmentGenerator,
                                     SegmentTrimmingGenerator vSegmentTrimmingGenerator,
                                     SegmentTrimmingGenerator jSegmentTrimmingGenerator,
                                     SegmentTrimming2Generator dSegmentTrimmingGenerator,
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
        SegmentId vSegment = vSegmentGenerator.generate(),
                jSegment = jSegmentGenerator.generate(),
                dSegment = djSegmentGenerator.generate(jSegment);
        var dTrimming = dSegmentTrimmingGenerator.generate(dSegment);
        int vTrimming = vSegmentTrimmingGenerator.generate(vSegment),
                jTrimming = jSegmentTrimmingGenerator.generate(vSegment),
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
