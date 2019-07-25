package com.antigenomics.mir.rearrangement;

import com.antigenomics.mir.rearrangement.converter.MuruganConverterUtils;
import com.antigenomics.mir.rearrangement.parser.MuruganModel;
import com.antigenomics.mir.rearrangement.parser.MuruganModelParserUtils;
import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.segment.SegmentLibrary;
import com.antigenomics.mir.Species;

public final class RearrangementModelUtils {
    public static RearrangementModel loadMuruganModel(SegmentLibrary segmentLibrary) {
        MuruganModel mdl = MuruganModelParserUtils.getModelFromResources(segmentLibrary.getSpecies(),
                segmentLibrary.getGene());

        var converter = MuruganConverterUtils.getConverter(mdl, segmentLibrary);

        return converter.getRearrangementModel();
    }

    public static RearrangementModel loadMuruganModel(Species species, Gene gene) {
        throw new UnsupportedOperationException(); // todo: built-in segments
    }

    public static RearrangementModel createEmptyModel(SegmentLibrary segmentLibrary) {
        return loadMuruganModel(segmentLibrary).getModelTemplate();
    }
}
