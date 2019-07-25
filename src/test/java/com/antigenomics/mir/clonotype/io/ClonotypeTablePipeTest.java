package com.antigenomics.mir.clonotype.io;

import com.antigenomics.mir.TestUtils;
import com.antigenomics.mir.clonotype.parser.MixcrClonotypeParserFactory;
import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.Species;
import com.antigenomics.mir.clonotype.parser.VDJtoolsClonotypeParserFactory;
import com.antigenomics.mir.segment.MigecSegmentLibraryUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ClonotypeTablePipeTest {
    @Test
    public void vdjtoolsReadTest() {
        var sampleSupplier = TestUtils.streamSupplierFrom("samples/trad_sample.txt.gz");
        var library = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRA);
        var parserFactory = new VDJtoolsClonotypeParserFactory(library, true);

        var clonotypeIterator = new ClonotypeTablePipe<>(sampleSupplier.get(), parserFactory);
        assertEquals(1051, clonotypeIterator.stream().count());


        clonotypeIterator = new ClonotypeTablePipe<>(sampleSupplier.get(), parserFactory);
        assertEquals(1051, clonotypeIterator.parallelStream().count());


        clonotypeIterator = new ClonotypeTableBufferedPipe<>(sampleSupplier.get(), parserFactory);
        assertEquals(1051, clonotypeIterator.stream().count());


        clonotypeIterator = new ClonotypeTableBufferedPipe<>(sampleSupplier.get(), parserFactory);
        assertEquals(1051, clonotypeIterator.parallelStream().count());
    }

    @Test
    public void mixcrReadTest() {
        var sampleSupplier = TestUtils.streamSupplierFrom("samples/mixcr_1.txt.gz");
        var library = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.IGH);
        var parserFactory = new MixcrClonotypeParserFactory(library, true);

        assertEquals(2507, new ClonotypeTablePipe<>(sampleSupplier.get(), parserFactory).stream().count());

        sampleSupplier = TestUtils.streamSupplierFrom("samples/mixcr_2.txt.gz");
        assertEquals(3589, new ClonotypeTablePipe<>(sampleSupplier.get(), parserFactory).stream().count());
    }
}