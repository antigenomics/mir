package com.milaboratory.mir.rearrangement.converter;

import com.milaboratory.mir.rearrangement.RearrangementTemplate;
import com.milaboratory.mir.rearrangement.parser.MuruganModeParserUtils;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.segment.parser.MigecSegmentLibraryUtils;
import org.junit.Test;

import java.io.IOException;

public class MuruganConverterUtilsTest {
    @Test
    public void test() throws IOException {
        var mdl = MuruganModeParserUtils.getModelFromResources(Species.Human, Gene.TRB);
        var segmLib = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRB);
        var converter = MuruganConverterUtils.getConverter(mdl, segmLib);
        var rearrMdl = converter.getRearrangementModel();

        long start = 0, end = 0;

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            ((RearrangementTemplate) rearrMdl.generate()).toRearrangement().getCdr3();
        }
        end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
    }
}
