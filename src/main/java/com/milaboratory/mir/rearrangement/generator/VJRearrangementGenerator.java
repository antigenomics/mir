package com.milaboratory.mir.rearrangement.generator;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;

public class VJRearrangementGenerator implements RearrangmentGenerator<VJRearrangementInfo> {
    private final SegmentGenerator<VariableSegment> vSegmentGenerator;
    private final SegmentGenerator<JoiningSegment> jSegmentGenerator;
    private final SegmentTrimmingGenerator<VariableSegment> vSegmentTrimmingGenerator;
    private final SegmentTrimmingGenerator<JoiningSegment> jSegmentTrimmingGenerator;
    private final InsertSizeGenerator insertSizeGenerator;
    private final SequenceGenerator<NucleotideSequence> vjInsertGenerator;

    public VJRearrangementGenerator(SegmentGenerator<VariableSegment> vSegmentGenerator,
                                    SegmentGenerator<JoiningSegment> jSegmentGenerator,
                                    SegmentTrimmingGenerator<VariableSegment> vSegmentTrimmingGenerator,
                                    SegmentTrimmingGenerator<JoiningSegment> jSegmentTrimmingGenerator,
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
        VariableSegment vSegment = vSegmentGenerator.generate();
        JoiningSegment jSegment = jSegmentGenerator.generate();

        int vTrimming = vSegmentTrimmingGenerator.generate(vSegment),
                jTrimming = jSegmentTrimmingGenerator.generate(jSegment),
                vjInsertSize = insertSizeGenerator.generate();

        NucleotideSequence vjInsert = vjInsertGenerator.generateForward(vjInsertSize);

        return new VJRearrangementInfo(vSegment, jSegment, vTrimming,
                jTrimming, vjInsertSize, vjInsert);
    }
}
