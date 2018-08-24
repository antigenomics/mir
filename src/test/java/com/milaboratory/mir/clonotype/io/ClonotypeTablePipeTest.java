package com.milaboratory.mir.clonotype.io;

import com.milaboratory.mir.TestUtils;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.clonotype.parser.VDJtoolsClonotypeParserFactory;
import com.milaboratory.mir.segment.parser.MigecSegmentLibraryUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class ClonotypeTablePipeTest {
    @Test
    public void readTest() throws IOException {
        var sampleSupplier = TestUtils.streamSupplierFrom("samples/trad_sample.txt");
        var library = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRA);
        var parserFactory = new VDJtoolsClonotypeParserFactory(library);


        var clonotypeIterator = new ClonotypeTablePipe<>(sampleSupplier.get(), parserFactory);
        assertEquals(1051, clonotypeIterator.stream().count());


        clonotypeIterator = new ClonotypeTablePipe<>(sampleSupplier.get(), parserFactory);
        assertEquals(1051, clonotypeIterator.parallelStream().count());


        clonotypeIterator = new ClonotypeTableBufferedPipe<>(sampleSupplier.get(), parserFactory);
        assertEquals(1051, clonotypeIterator.stream().count());


        clonotypeIterator = new ClonotypeTableBufferedPipe<>(sampleSupplier.get(), parserFactory);
        assertEquals(1051, clonotypeIterator.parallelStream().count());
    }
}