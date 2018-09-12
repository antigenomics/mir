package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.SequenceUtils;
import com.milaboratory.mir.mappers.markup.ArrayBasedSequenceRegionMarkup;
import com.milaboratory.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkupUtils;
import com.milaboratory.mir.structure.AntigenReceptorRegionType;

public class VariableSegmentImpl implements VariableSegment {
    private final String id;
    private final NucleotideSequence germline,
            cdr3Part, cdr3PartWithP;
    private final int referencePoint;
    private final PrecomputedSequenceRegionMarkup<NucleotideSequence, AntigenReceptorRegionType> regionMarkupNt;
    private final PrecomputedSequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType> regionMarkupAa;
    private final boolean majorAllele;

    // todo: store cdr1-2 nt&aa

    public VariableSegmentImpl(String id,
                               NucleotideSequence germline,
                               int cdr1Start, int cdr1End, int cdr2Start, int cdr2End, int referencePoint,
                               boolean majorAllele) {
        this.id = id;
        this.germline = germline;
        this.referencePoint = referencePoint;
        this.majorAllele = majorAllele;

        this.cdr3Part = germline.getRange(referencePoint - 3, // V reference point = 0-based coord of point directly after Cys codon
                germline.size());
        this.cdr3PartWithP = cdr3Part.concatenate(cdr3Part.getReverseComplement());

        this.regionMarkupNt = new ArrayBasedSequenceRegionMarkup<>(
                germline,
                new int[]{0, cdr1Start, cdr1End, cdr2Start, cdr2End, referencePoint, -1}, // mark end as incomplete
                AntigenReceptorRegionType.class
        ).asPrecomputed();
        this.regionMarkupAa = SequenceRegionMarkupUtils.translate(regionMarkupNt,
                false); // trim 3' in CDR3
    }

    @Override
    public NucleotideSequence getTrimmedCdr3Part(int trimmingSize) {
        return SequenceUtils.getSequenceRangeSafe(cdr3PartWithP,
                0, cdr3Part.size() - trimmingSize);
    }

    @Override
    public NucleotideSequence getCdr3Part() {
        return cdr3Part;
    }

    @Override
    public NucleotideSequence getCdr3PartWithP() {
        return cdr3Part;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isMissingInLibrary() {
        return false;
    }

    @Override
    public NucleotideSequence getGermlineSequence() {
        return germline;
    }

    @Override
    public int getReferencePoint() {
        return referencePoint;
    }

    @Override
    public SequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType> getRegionMarkupAa() {
        return regionMarkupAa;
    }

    @Override
    public SequenceRegionMarkup<NucleotideSequence, AntigenReceptorRegionType> getRegionMarkupNt() {
        return regionMarkupNt;
    }

    @Override
    public boolean isMajorAllele() {
        return majorAllele;
    }

    @Override
    public String toString() {
        return id + "\t" + cdr3Part;
    }
}
