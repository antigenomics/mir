package com.milaboratory.mir.rearrangement;

import com.milaboratory.mir.rearrangement.converter.MuruganConverterUtils;
import com.milaboratory.mir.rearrangement.parser.MuruganModeParserUtils;
import com.milaboratory.mir.rearrangement.parser.MuruganModel;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.SegmentLibrary;
import com.milaboratory.mir.segment.Species;

import java.io.IOException;

public final class RearrangementModelUtils {
    public static RearrangementModel loadMuruganModel(SegmentLibrary segmentLibrary) {
        MuruganModel mdl = MuruganModeParserUtils.getModelFromResources(segmentLibrary.getSpecies(),
                segmentLibrary.getGene());

        var converter = MuruganConverterUtils.getConverter(mdl, segmentLibrary);

        return converter.getRearrangementModel();
    }

    public static RearrangementModel loadMuruganModel(Species species, Gene gene) {
        throw new UnsupportedOperationException(); // todo: built-in segments
    }

    public static RearrangementModel createEmptyModel(SegmentLibrary segmentLibrary) {
        throw new UnsupportedOperationException(); // todo: creating empty model for training
    }
}
