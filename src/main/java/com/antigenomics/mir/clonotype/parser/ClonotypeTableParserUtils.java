package com.antigenomics.mir.clonotype.parser;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.clonotype.io.ClonotypeTableBufferedPipe;
import com.antigenomics.mir.clonotype.io.ClonotypeTablePipe;
import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.segment.SegmentLibrary;
import com.antigenomics.mir.Species;
import com.antigenomics.mir.segment.MigecSegmentLibraryUtils;

import java.io.InputStream;

public final class ClonotypeTableParserUtils {
    public static ClonotypeTablePipe<? extends Clonotype> streamFrom(InputStream inputStream,
                                                                     Software software,
                                                                     SegmentLibrary segmentLibrary,
                                                                     boolean majorAllelesOnly,
                                                                     boolean buffered) {
        ClonotypeTableParserFactory<? extends Clonotype> parserFactory;
        switch (software) {
            case VDJtools:
                parserFactory = new VDJtoolsClonotypeParserFactory(segmentLibrary, majorAllelesOnly);
                break;

            case MIXCR:
                parserFactory = new MixcrClonotypeParserFactory(segmentLibrary, majorAllelesOnly);
                break;

            case VDJdb:
                parserFactory = new VDJdbClonotypeParserFactory();
                break;

            default:
                throw new IllegalArgumentException("Software " + software + " not supported");
        }

        return buffered ?
                new ClonotypeTableBufferedPipe<>(inputStream, parserFactory) :
                new ClonotypeTablePipe<>(inputStream, parserFactory);
    }

    public static ClonotypeTablePipe<? extends Clonotype> streamFrom(InputStream inputStream,
                                                                     Software software,
                                                                     SegmentLibrary segmentLibrary,
                                                                     boolean majorAllelesOnly) {
        return streamFrom(inputStream, software, segmentLibrary, majorAllelesOnly, true);
    }

    public static ClonotypeTablePipe<? extends Clonotype> streamFrom(InputStream inputStream,
                                                                     Software software,
                                                                     SegmentLibrary segmentLibrary) {
        return streamFrom(inputStream, software, segmentLibrary, true);
    }

    public static ClonotypeTablePipe<? extends Clonotype> streamFrom(InputStream inputStream,
                                                                     Software software,
                                                                     Species species, Gene gene) {
        return streamFrom(inputStream, software,
                MigecSegmentLibraryUtils.getLibraryFromResources(species, gene));
    }
}
