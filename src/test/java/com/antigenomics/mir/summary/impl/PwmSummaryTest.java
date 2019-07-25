package com.antigenomics.mir.summary.impl;

import com.antigenomics.mir.SequenceUtils;
import com.antigenomics.mir.TestUtils;
import com.antigenomics.mir.clonotype.io.ClonotypeTablePipe;
import com.antigenomics.mir.clonotype.parser.VDJtoolsClonotypeParserFactory;
import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.Species;
import com.antigenomics.mir.segment.MigecSegmentLibraryUtils;
import com.antigenomics.mir.summary.ClonotypeSummaryTableHelper;
import com.antigenomics.mir.summary.ClonotypeSummaryType;
import org.junit.Assert;
import org.junit.Test;

public class PwmSummaryTest {
    @Test
    public void vdjtoolsReadTest() {
        var sampleSupplier = TestUtils.streamSupplierFrom("samples/trad_sample.txt.gz");
        var library = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRA);
        var parserFactory = new VDJtoolsClonotypeParserFactory(library, true);

        var table = ClonotypeSummaryTableHelper.create(ClonotypeSummaryType.AA_CDR3_PWM,
                true);
        new ClonotypeTablePipe<>(sampleSupplier.get(), parserFactory)
                .parallelStream()
                .forEach(table);

        var res = table
                .getCounters()
                .stream()
                .mapToDouble(x -> ((PwmGroupSummaryEntry) x).getValue()).sum();

        var totalAas = new ClonotypeTablePipe<>(sampleSupplier.get(), parserFactory)
                .stream()
                .filter(x -> !SequenceUtils.isNonCoding(x.getCdr3Aa()))
                .mapToDouble(x -> x.getFrequency() * x.getCdr3Aa().size())
                .sum();

        Assert.assertEquals(totalAas, res, 1e-6);
    }
}