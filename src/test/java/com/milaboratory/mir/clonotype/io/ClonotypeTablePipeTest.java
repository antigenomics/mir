package com.milaboratory.mir.clonotype.io;

import com.milaboratory.mir.TestUtils;
import com.milaboratory.mir.clonotype.parser.MixcrClonotypeParserFactory;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.clonotype.parser.VDJtoolsClonotypeParserFactory;
import com.milaboratory.mir.segment.parser.MigecSegmentLibraryUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class ClonotypeTablePipeTest {
    @Test
    public void vdjtoolsReadTest() {
        var sampleSupplier = TestUtils.streamSupplierFrom("samples/trad_sample.txt");
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
        var sampleSupplier = TestUtils.streamSupplierFrom("samples/mixcr_1.txt");
        var library = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.IGH);
        var parserFactory = new MixcrClonotypeParserFactory(library, true);


        var clonotypeIterator = new ClonotypeTablePipe<>(sampleSupplier.get(), parserFactory);
        assertEquals(2507, clonotypeIterator.stream().count());


        clonotypeIterator = new ClonotypeTablePipe<>(sampleSupplier.get(), parserFactory);
        assertEquals(2507, clonotypeIterator.parallelStream().count());


        clonotypeIterator = new ClonotypeTableBufferedPipe<>(sampleSupplier.get(), parserFactory);
        assertEquals(2507, clonotypeIterator.stream().count());


        clonotypeIterator = new ClonotypeTableBufferedPipe<>(sampleSupplier.get(), parserFactory);
        assertEquals(2507, clonotypeIterator.parallelStream().count());
    }
}