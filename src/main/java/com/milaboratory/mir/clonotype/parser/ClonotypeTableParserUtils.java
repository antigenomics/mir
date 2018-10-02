package com.milaboratory.mir.clonotype.parser;

import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.clonotype.io.ClonotypeTableBufferedPipe;
import com.milaboratory.mir.clonotype.io.ClonotypeTablePipe;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.SegmentLibrary;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.segment.parser.MigecSegmentLibraryUtils;

import java.io.InputStream;

public class ClonotypeTableParserUtils {
    public ClonotypeTablePipe<? extends Clonotype> streamFrom(InputStream inputStream,
                                                              Software software,
                                                              SegmentLibrary segmentLibrary,
                                                              boolean majorAllelesOnly,
                                                              boolean buffered) {
        switch (software) {
            case VDJtools:
                var parserFactory = new VDJtoolsClonotypeParserFactory(segmentLibrary, majorAllelesOnly);
                return buffered ?
                        new ClonotypeTableBufferedPipe<>(inputStream, parserFactory) :
                        new ClonotypeTablePipe<>(inputStream, parserFactory);
        }

        throw new IllegalArgumentException("Software " + software + " not supported");
    }

    public ClonotypeTablePipe<? extends Clonotype> streamFrom(InputStream inputStream,
                                                              Software software,
                                                              SegmentLibrary segmentLibrary,
                                                              boolean majorAllelesOnly) {
        return streamFrom(inputStream, software, segmentLibrary, majorAllelesOnly, true);
    }

    public ClonotypeTablePipe<? extends Clonotype> streamFrom(InputStream inputStream,
                                                              Software software,
                                                              SegmentLibrary segmentLibrary) {
        return streamFrom(inputStream, software, segmentLibrary, true);
    }

    public ClonotypeTablePipe<? extends Clonotype> streamFrom(InputStream inputStream,
                                                              Software software,
                                                              Species species, Gene gene) {
        return streamFrom(inputStream, software,
                MigecSegmentLibraryUtils.getLibraryFromResources(species, gene));
    }
}
