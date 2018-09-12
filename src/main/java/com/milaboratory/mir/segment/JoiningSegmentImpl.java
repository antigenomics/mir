package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.SequenceUtils;
import com.milaboratory.mir.mappers.markup.ArrayBasedSequenceRegionMarkup;
import com.milaboratory.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkupUtils;
import com.milaboratory.mir.structure.AntigenReceptorRegionType;

public class JoiningSegmentImpl implements JoiningSegment {
    private final String id;
    private final NucleotideSequence germline, cdr3Part, cdr3PartWithP;
    private final PrecomputedSequenceRegionMarkup<NucleotideSequence, AntigenReceptorRegionType> regionMarkupNt;
    private final PrecomputedSequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType> regionMarkupAa;
    private final int referencePoint;
    private final boolean majorAllele;

    public JoiningSegmentImpl(String id,
                              NucleotideSequence germline,
                              int referencePoint,
                              boolean majorAllele) {
        this.id = id;
        this.germline = germline;
        this.referencePoint = referencePoint;
        this.cdr3Part = germline.getRange(0,
                referencePoint + 4); // J reference point is the 0-base coordinate of first base before Phe/Trp
        this.cdr3PartWithP = cdr3Part.getReverseComplement().concatenate(cdr3Part);
        this.majorAllele = majorAllele;

        this.regionMarkupNt = new ArrayBasedSequenceRegionMarkup<>(
                germline,
                new int[]{-1, referencePoint, germline.size()}, // mark start as incomplete
                AntigenReceptorRegionType.class
        ).asPrecomputed();
        this.regionMarkupAa = SequenceRegionMarkupUtils.translate(regionMarkupNt,
                true); // trim 5' in CDR3
    }

    @Override
    public NucleotideSequence getTrimmedCdr3Part(int trimmingSize) {
        int size = cdr3Part.size();
        return SequenceUtils.getSequenceRangeSafe(cdr3PartWithP,
                size + trimmingSize, 2 * size);
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
    public boolean isMajorAllele() {
        return majorAllele;
    }

    @Override
    public NucleotideSequence getGermlineSequence() {
        return germline;
    }

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
    public String toString() {
        return id + "\t" + cdr3Part;
    }
}
