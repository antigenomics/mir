package com.milaboratory.mir.summary.counters;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.clonotype.rearrangement.JunctionMarkup;
import com.milaboratory.mir.clonotype.rearrangement.ReadlessClonotypeImpl;
import com.milaboratory.mir.clonotype.rearrangement.SegmentTrimming;
import com.milaboratory.mir.segment.*;
import com.milaboratory.mir.summary.*;
import com.milaboratory.mir.summary.binning.DummyGroup;
import com.milaboratory.mir.summary.binning.DummyGroupWrapper;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class Cdr3NtLenSummaryEntryTest {

    static WrappedClonotype getDummyWrappedClonotypeWithSeq(NucleotideSequence cdr3Nt){
        return new WrappedClonotype(DummyGroup.INSTANCE, 1, new ReadlessClonotypeImpl(
                cdr3Nt,
                MissingVariableSegment.INSTANCE,
                MissingDiversitySegment.INSTANCE,
                MissingJoiningSegment.INSTANCE,
                MissingConstantSegment.INSTANCE,
                new SegmentTrimming(-1, -1, -1, -1),
                new JunctionMarkup(-1, -1, -1, -1)));
    }
    @Test
    public void test(){
        var summaryTable = new ClonotypeSummaryTable<Clonotype, DummyGroup,
                Cdr3NtLenSummaryEntry<DummyGroup>, Cdr3NtLenSummary<Clonotype, DummyGroup>>(new DummyGroupWrapper<>(),
                Cdr3NtLenSummary::new);
        summaryTable.accept(new ReadlessClonotypeImpl(
                new NucleotideSequence("TGTGCGATTTGGGATGTGGGACTGCGACATGACTTCTGG"),
                MissingVariableSegment.INSTANCE,
                MissingDiversitySegment.INSTANCE,
                MissingJoiningSegment.INSTANCE,
                MissingConstantSegment.INSTANCE,
                new SegmentTrimming(-1, -1, -1, -1),
                new JunctionMarkup(-1, -1, -1, -1)));

        var cdr3NtLenSummaryEntry = new Cdr3NtLenSummary<>(DummyGroup.INSTANCE);


        cdr3NtLenSummaryEntry.accept(getDummyWrappedClonotypeWithSeq(new NucleotideSequence("TGTGCGATTTGGGATGTGGGACTGCGACATGACTTCGG")));
        cdr3NtLenSummaryEntry.accept(getDummyWrappedClonotypeWithSeq(new NucleotideSequence("TGTGCGATTTGGGATGTGGGACTGCGACATGATGACTTCGG")));
        cdr3NtLenSummaryEntry.accept(getDummyWrappedClonotypeWithSeq(new NucleotideSequence("TGTGCGATTTGGGATGTGGGACTGCGACATGAACACTTCGG")));
        cdr3NtLenSummaryEntry.accept(getDummyWrappedClonotypeWithSeq(new NucleotideSequence("TGTGCGATTTGGGATGTGGGACTGCGACATGAACACTTCGG")));
        cdr3NtLenSummaryEntry.accept(getDummyWrappedClonotypeWithSeq(new NucleotideSequence("TGTGCGATTTGGGATGTGGGACTGCGACATGATGATGACTTCGG")));
        cdr3NtLenSummaryEntry.accept(getDummyWrappedClonotypeWithSeq(new NucleotideSequence("TGTGCGATTTGGGATGTGGGACTGCGACATGACTTCGG")));
        assertEquals(2, cdr3NtLenSummaryEntry.getCount("TGTGCGATTTGGGATGTGGGACTGCGACATGACTTCGG".length()), 0.1);
        assertEquals(3, cdr3NtLenSummaryEntry.getCount("TGTGCGATTTGGGATGTGGGACTGCGACATGATGACTTCGG".length()), 0.1);
        assertEquals(1, cdr3NtLenSummaryEntry.getCount("TGTGCGATTTGGGATGTGGGACTGCGACATGATGATGACTTCGG".length()), 0.1);
        assertEquals(3, cdr3NtLenSummaryEntry.getEntries().size(), 0.1);
    }

}