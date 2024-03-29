package com.antigenomics.mir.segment;

import com.antigenomics.mir.structure.AntigenReceptorRegionType;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.antigenomics.mir.SequenceUtils;
import com.antigenomics.mir.mappers.markup.ArrayBasedSequenceRegionMarkup;
import com.antigenomics.mir.mappers.markup.PrecomputedSequenceRegionMarkup;
import com.antigenomics.mir.mappers.markup.SequenceRegionMarkupUtils;

import java.util.Objects;

public class VariableSegmentImpl implements VariableSegment {
    private final String id;
    private final NucleotideSequence germlineNt, cdr3Part, cdr3PartWithP;
    private final int referencePoint;
    private final PrecomputedSequenceRegionMarkup<NucleotideSequence, AntigenReceptorRegionType> regionMarkupNt;
    private final PrecomputedSequenceRegionMarkup<AminoAcidSequence, AntigenReceptorRegionType> regionMarkupAa;
    private final boolean majorAllele;

    public static VariableSegmentImpl mock(String id) {
        return new VariableSegmentImpl(id, new NucleotideSequence("TGT"),
                0, 0, 0, 0, 3,
                true);
    }

    public VariableSegmentImpl(String id,
                               NucleotideSequence germlineNt,
                               int cdr1Start, int cdr1End, int cdr2Start, int cdr2End, int referencePoint,
                               boolean majorAllele) {
        this.id = id;
        this.germlineNt = germlineNt;
        this.referencePoint = referencePoint;
        this.majorAllele = majorAllele;
        int cdr3Start = referencePoint - 3; // V reference point = 0-based coord of point directly after Cys codon
        this.cdr3Part = germlineNt.getRange(cdr3Start, germlineNt.size());
        this.cdr3PartWithP = cdr3Part.concatenate(cdr3Part.getReverseComplement());

        this.regionMarkupNt = new ArrayBasedSequenceRegionMarkup<>(
                germlineNt,
                new int[]{0, cdr1Start, cdr1End, cdr2Start, cdr2End, cdr3Start, // fr1-cdr3
                        germlineNt.size(), germlineNt.size()}, // fr4 missing
                AntigenReceptorRegionType.class
        ).asPrecomputed();
        this.regionMarkupAa = SequenceRegionMarkupUtils.translateWithAnchor(regionMarkupNt, cdr3Start);
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
    public NucleotideSequence getGermlineSequenceNt() {
        return germlineNt;
    }

    @Override
    public NucleotideSequence getGermlineSequenceNtWithoutCdr3() {
        return germlineNt.getRange(0, germlineNt.size() - cdr3Part.size());
    }

    @Override
    public AminoAcidSequence getGermlineSequenceAa() {
        return regionMarkupAa.getFullSequence();
    }

    @Override
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
    public boolean isMajorAllele() {
        return majorAllele;
    }

    @Override
    public String toString() {
        return id + "\t" + cdr3Part;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VariableSegmentImpl that = (VariableSegmentImpl) o;
        return majorAllele == that.majorAllele &&
                Objects.equals(id, that.id) &&
                Objects.equals(regionMarkupNt, that.regionMarkupNt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, regionMarkupNt, majorAllele);
    }
}
