package com.milaboratory.mir.clonotype.parser;

import com.milaboratory.mir.clonotype.ClonotypeCall;
import com.milaboratory.mir.clonotype.io.ClonotypeTableBufferedPipe;
import com.milaboratory.mir.clonotype.io.ClonotypeTablePipe;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.SegmentLibrary;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.segment.parser.MigecSegmentLibraryUtils;

import java.io.InputStream;

public final class ClonotypeTableParserUtils {
    public static ClonotypeTablePipe<? extends ClonotypeCall> streamFrom(InputStream inputStream,
                                                              Software software,
                                                              SegmentLibrary segmentLibrary,
                                                              boolean majorAllelesOnly,
                                                              boolean buffered) {
        ClonotypeTableParserFactory parserFactory;
        switch (software) {
            case VDJtools:
                parserFactory = new VDJtoolsClonotypeParserFactory(segmentLibrary, majorAllelesOnly);
                break;

            case MIXCR:
                parserFactory = new MixcrClonotypeParserFactory(segmentLibrary, majorAllelesOnly);
                break;

            default:
                throw new IllegalArgumentException("Software " + software + " not supported");
        }

        return buffered ?
                new ClonotypeTableBufferedPipe<>(inputStream, parserFactory) :
                new ClonotypeTablePipe<>(inputStream, parserFactory);
    }

    public static ClonotypeTablePipe<? extends ClonotypeCall> streamFrom(InputStream inputStream,
                                                                         Software software,
                                                                         SegmentLibrary segmentLibrary,
                                                                         boolean majorAllelesOnly) {
        return streamFrom(inputStream, software, segmentLibrary, majorAllelesOnly, true);
    }

    public static ClonotypeTablePipe<? extends ClonotypeCall> streamFrom(InputStream inputStream,
                                                              Software software,
                                                              SegmentLibrary segmentLibrary) {
        return streamFrom(inputStream, software, segmentLibrary, true);
    }

    public static ClonotypeTablePipe<? extends ClonotypeCall> streamFrom(InputStream inputStream,
                                                              Software software,
                                                              Species species, Gene gene) {
        return streamFrom(inputStream, software,
                MigecSegmentLibraryUtils.getLibraryFromResources(species, gene));
    }
}
