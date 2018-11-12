package com.milaboratory.mir.segment;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.SequenceUtils;
import com.milaboratory.mir.mappers.markup.ArrayBasedSequenceRegionMarkup;
import com.milaboratory.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkupUtils;
import com.milaboratory.mir.structure.AntigenReceptorRegionType;

import java.util.Objects;

public class JoiningSegmentImpl implements JoiningSegment {
    private final String id;
    private final NucleotideSequence germlineNt, cdr3Part, cdr3PartWithP;
    private final PrecomputedSequenceRegionMarkup<NucleotideSequence, AntigenReceptorRegionType> regionMarkupNt;
    private final PrecomputedSequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType> regionMarkupAa;
    private final int referencePoint;
    private final boolean majorAllele;

    public JoiningSegmentImpl(String id,
                              NucleotideSequence germlineNt,
                              int referencePoint,
                              boolean majorAllele) {
        this.id = id;
        this.germlineNt = germlineNt;
        this.referencePoint = referencePoint;
        int cdr3End = referencePoint + 4; // J reference point is the 0-base coordinate of first base before Phe/Trp
        this.cdr3Part = germlineNt.getRange(0, cdr3End);
        this.cdr3PartWithP = cdr3Part.getReverseComplement().concatenate(cdr3Part);
        this.majorAllele = majorAllele;

        this.regionMarkupNt = new ArrayBasedSequenceRegionMarkup<>(
                germlineNt,
                new int[]{0, 0, 0, 0, 0, // fr1 - fr3 missing
                        0, cdr3End, germlineNt.size()}, // cdr3-fr4 markup
                AntigenReceptorRegionType.class
        ).asPrecomputed();
        this.regionMarkupAa = SequenceRegionMarkupUtils.translateWithAnchor(regionMarkupNt, cdr3End);
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
    public NucleotideSequence getGermlineSequenceNt() {
        return germlineNt;
    }

    @Override
    public AminoAcidSequence getGermlineSequenceAa() {
        return regionMarkupAa.getFullSequence();
    }

    public int getReferencePoint() {
        return referencePoint;
    }

    @Override
    public PrecomputedSequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType> getRegionMarkupAa() {
        return regionMarkupAa;
    }

    @Override
    public PrecomputedSequenceRegionMarkup<NucleotideSequence, AntigenReceptorRegionType> getRegionMarkupNt() {
        return regionMarkupNt;
    }

    @Override
    public String toString() {
        return id + "\t" + cdr3Part;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoiningSegmentImpl that = (JoiningSegmentImpl) o;
        return majorAllele == that.majorAllele &&
                Objects.equals(id, that.id) &&
                Objects.equals(regionMarkupNt, that.regionMarkupNt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, regionMarkupNt, majorAllele);
    }
}
