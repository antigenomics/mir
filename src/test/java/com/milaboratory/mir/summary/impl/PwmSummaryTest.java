package com.milaboratory.mir.summary.impl;

import com.milaboratory.mir.SequenceUtils;
import com.milaboratory.mir.TestUtils;
import com.milaboratory.mir.clonotype.io.ClonotypeTablePipe;
import com.milaboratory.mir.clonotype.parser.VDJtoolsClonotypeParserFactory;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.segment.parser.MigecSegmentLibraryUtils;
import com.milaboratory.mir.summary.ClonotypeSummaryTableHelper;
import com.milaboratory.mir.summary.ClonotypeSummaryType;
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