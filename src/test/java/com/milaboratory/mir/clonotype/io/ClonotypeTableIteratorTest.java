package com.milaboratory.mir.clonotype.io;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.clonotype.ClonotypeCall;
import com.milaboratory.mir.clonotype.parser.VDJtoolsClonotypeParserFactory;
import com.milaboratory.mir.io.StreamHelper;
import com.milaboratory.mir.segment.parser.MigecSegmentLibraryUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class ClonotypeTableIteratorTest {
    @Test
    public void readTest() throws IOException {
        var sampleStream = CommonUtils.getResourceAsStream("samples/trad_sample.txt");
        var library = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRA);
        var parserFactory = new VDJtoolsClonotypeParserFactory(library);
        var iterator = new ClonotypeTableIterator<>(sampleStream, parserFactory);
        assertEquals(1051, StreamHelper.asStream(iterator).count());

        sampleStream = CommonUtils.getResourceAsStream("samples/trad_sample.txt");
        iterator = new ClonotypeTableIterator<>(sampleStream, parserFactory);
        assertEquals(1051, StreamHelper.asParallelStream(iterator,
                ClonotypeCall.getDummy(), 50).count());
    }
}