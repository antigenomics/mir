package com.antigenomics.mir.summary.impl;

import com.antigenomics.mir.summary.WrappedClonotype;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.antigenomics.mir.clonotype.rearrangement.ReadlessClonotypeImpl;
import com.antigenomics.mir.summary.*;
import com.antigenomics.mir.summary.binning.DummyGroup;
import org.junit.Test;

import static org.junit.Assert.*;

public class Cdr3LenSummaryTest {
    static WrappedClonotype wrap(String cdr3Nt) {
        return WrappedClonotype.dummy(new ReadlessClonotypeImpl(new NucleotideSequence(cdr3Nt)));
    }

    @Test
    public void testNt() {
        var cdr3NtLenSummary = new Cdr3NtLenSummary<>(DummyGroup.INSTANCE);

        cdr3NtLenSummary.accept(wrap("TGTGCGATTTGGGATGTGGGACTGCGACATGACTTCGG"));
        cdr3NtLenSummary.accept(wrap("TGTGCGATTTGGGATGTGGGACTGCGACATGATGACTTCGG"));
        cdr3NtLenSummary.accept(wrap("TGTGCGATTTGGGATGTGGGACTGCGACATGAACACTTCGG"));
        cdr3NtLenSummary.accept(wrap("TGTGCGATTTGGGATGTGGGACTGCGACATGAACACTTCGG"));
        cdr3NtLenSummary.accept(wrap("TGTGCGATTTGGGATGTGGGACTGCGACATGATGATGACTTCGG"));
        cdr3NtLenSummary.accept(wrap("TGTGCGATTTGGGATGTGGGACTGCGACATGACTTCGG"));

        assertEquals(2, cdr3NtLenSummary.getCount("TGTGCGATTTGGGATGTGGGACTGCGACATGACTTCGG".length()), 0.0);
        assertEquals(3, cdr3NtLenSummary.getCount("TGTGCGATTTGGGATGTGGGACTGCGACATGATGACTTCGG".length()), 0.0);
        assertEquals(1, cdr3NtLenSummary.getCount("TGTGCGATTTGGGATGTGGGACTGCGACATGATGATGACTTCGG".length()), 0.0);
        assertEquals(3, cdr3NtLenSummary.getCounters().size(), 0.0);
    }

    @Test
    public void testAa() {
        var cdr3AaLenSummary = new Cdr3AaLenSummary<>(DummyGroup.INSTANCE);

        cdr3AaLenSummary.accept(wrap("TGTGCGATTTGGGATGTGGGACTGCACATGACTTTCGG"));
        cdr3AaLenSummary.accept(wrap("TGTGCGATTTGGGATGTGGGACTGCGACATGACTTTCGG"));
        cdr3AaLenSummary.accept(wrap("TGTGCGATTTGGGATGTGGGACAAATGCGACATGACTTTCGG"));
        cdr3AaLenSummary.accept(wrap("TGTGCGATTTGGGATGTGGGACAAATGCGACATGACTTTCGG"));
        cdr3AaLenSummary.accept(wrap("TGTGCGATTTGGGATGTGGGACAAATGCGACATGACTTTCGG"));
        cdr3AaLenSummary.accept(wrap("TGTGCGATTTGGGATGTGGGACTAAAAAAGCGACATGACTTTCGG"));
        cdr3AaLenSummary.accept(wrap("TGTGCGATTTGGGATGTGGGACTGCGACATGACTTTCGG"));

        assertEquals(2, cdr3AaLenSummary.getCount("TGTGCGATTTGGGATGTGGGACTGCGACATGACTTTCGG".length() / 3), 0.0);
        assertEquals(3, cdr3AaLenSummary.getCount("TGTGCGATTTGGGATGTGGGACAAATGCGACATGACTTTCGG".length() / 3), 0.0);
        assertEquals(1, cdr3AaLenSummary.getCount("TGTGCGATTTGGGATGTGGGACTAAAAAAGCGACATGACTTTCGG".length() / 3), 0.0);
        assertEquals(3, cdr3AaLenSummary.getCounters().size(), 0.0);
    }

}