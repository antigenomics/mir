package com.milaboratory.mir.model.generator;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.segment.SegmentId;

public class VJRearrangementGenerator implements RearrangmentGenerator<VJRearrangementInfo> {
    private final SegmentGenerator vSegmentGenerator, jSegmentGenerator;
    private final SegmentTrimmingGenerator vSegmentTrimmingGenerator,
            jSegmentTrimmingGenerator;
    private final InsertSizeGenerator insertSizeGenerator;
    private final SequenceGenerator<NucleotideSequence> vjInsertGenerator;

    public VJRearrangementGenerator(SegmentGenerator vSegmentGenerator,
                                    SegmentGenerator jSegmentGenerator,
                                    SegmentTrimmingGenerator vSegmentTrimmingGenerator,
                                    SegmentTrimmingGenerator jSegmentTrimmingGenerator,
                                    InsertSizeGenerator insertSizeGenerator,
                                    SequenceGenerator<NucleotideSequence> vjInsertGenerator) {
        this.vSegmentGenerator = vSegmentGenerator;
        this.jSegmentGenerator = jSegmentGenerator;
        this.vSegmentTrimmingGenerator = vSegmentTrimmingGenerator;
        this.jSegmentTrimmingGenerator = jSegmentTrimmingGenerator;
        this.insertSizeGenerator = insertSizeGenerator;
        this.vjInsertGenerator = vjInsertGenerator;
    }

    @Override
    public VJRearrangementInfo generate() {
        SegmentId vSegment = vSegmentGenerator.generate(),
                jSegment = jSegmentGenerator.generate();
        int vTrimming = vSegmentTrimmingGenerator.generate(vSegment),
                jTrimming = jSegmentTrimmingGenerator.generate(vSegment),
                vjInsertSize = insertSizeGenerator.generate();

        NucleotideSequence vjInsert = vjInsertGenerator.generateForward(vjInsertSize);

        return new VJRearrangementInfo(vSegment, jSegment, vTrimming,
                jTrimming, vjInsertSize, vjInsert);
    }
}
