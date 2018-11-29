package com.milaboratory.mir.summary.counters;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.rearrangement.ReadlessClonotypeImpl;
import com.milaboratory.mir.summary.*;
import com.milaboratory.mir.summary.binning.DummyGroup;
import org.junit.Test;

import static org.junit.Assert.*;

public class Cdr3LenSummaryEntryTest {

    static WrappedClonotype wrap(String cdr3Nt) {
        return WrappedClonotype.dummy(new ReadlessClonotypeImpl(new NucleotideSequence(cdr3Nt)));
    }

    @Test
    public void testNt() {
        var cdr3NtLenSummaryEntry = new Cdr3NtLenSummary<>(DummyGroup.INSTANCE);

        cdr3NtLenSummaryEntry.accept(wrap("TGTGCGATTTGGGATGTGGGACTGCGACATGACTTCGG"));
        cdr3NtLenSummaryEntry.accept(wrap("TGTGCGATTTGGGATGTGGGACTGCGACATGATGACTTCGG"));
        cdr3NtLenSummaryEntry.accept(wrap("TGTGCGATTTGGGATGTGGGACTGCGACATGAACACTTCGG"));
        cdr3NtLenSummaryEntry.accept(wrap("TGTGCGATTTGGGATGTGGGACTGCGACATGAACACTTCGG"));
        cdr3NtLenSummaryEntry.accept(wrap("TGTGCGATTTGGGATGTGGGACTGCGACATGATGATGACTTCGG"));
        cdr3NtLenSummaryEntry.accept(wrap("TGTGCGATTTGGGATGTGGGACTGCGACATGACTTCGG"));

        assertEquals(2, cdr3NtLenSummaryEntry.getCount("TGTGCGATTTGGGATGTGGGACTGCGACATGACTTCGG".length()), 0.0);
        assertEquals(3, cdr3NtLenSummaryEntry.getCount("TGTGCGATTTGGGATGTGGGACTGCGACATGATGACTTCGG".length()), 0.0);
        assertEquals(1, cdr3NtLenSummaryEntry.getCount("TGTGCGATTTGGGATGTGGGACTGCGACATGATGATGACTTCGG".length()), 0.0);
        assertEquals(3, cdr3NtLenSummaryEntry.getEntries().size(), 0.0);
    }

    @Test
    public void testAa() {
        var cdr3AaLenSummaryEntry = new Cdr3AaLenSummary<>(DummyGroup.INSTANCE);

        cdr3AaLenSummaryEntry.accept(wrap("TGTGCGATTTGGGATGTGGGACTGCACATGACTTTCGG"));
        cdr3AaLenSummaryEntry.accept(wrap("TGTGCGATTTGGGATGTGGGACTGCGACATGACTTTCGG"));
        cdr3AaLenSummaryEntry.accept(wrap("TGTGCGATTTGGGATGTGGGACAAATGCGACATGACTTTCGG"));
        cdr3AaLenSummaryEntry.accept(wrap("TGTGCGATTTGGGATGTGGGACAAATGCGACATGACTTTCGG"));
        cdr3AaLenSummaryEntry.accept(wrap("TGTGCGATTTGGGATGTGGGACAAATGCGACATGACTTTCGG"));
        cdr3AaLenSummaryEntry.accept(wrap("TGTGCGATTTGGGATGTGGGACTAAAAAAGCGACATGACTTTCGG"));
        cdr3AaLenSummaryEntry.accept(wrap("TGTGCGATTTGGGATGTGGGACTGCGACATGACTTTCGG"));

        assertEquals(2, cdr3AaLenSummaryEntry.getCount("TGTGCGATTTGGGATGTGGGACTGCGACATGACTTTCGG".length() / 3), 0.0);
        assertEquals(3, cdr3AaLenSummaryEntry.getCount("TGTGCGATTTGGGATGTGGGACAAATGCGACATGACTTTCGG".length() / 3), 0.0);
        assertEquals(1, cdr3AaLenSummaryEntry.getCount("TGTGCGATTTGGGATGTGGGACTAAAAAAGCGACATGACTTTCGG".length() / 3), 0.0);
        assertEquals(3, cdr3AaLenSummaryEntry.getEntries().size(), 0.0);
    }

}