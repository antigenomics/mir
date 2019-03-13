package com.antigenomics.mir.rearrangement.converter;

import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.segment.Species;
import org.junit.Test;

import static com.antigenomics.mir.rearrangement.converter.MuruganConverterUtils.*;
import static com.antigenomics.mir.rearrangement.parser.MuruganModelParserUtils.getModelFromResources;
import static com.antigenomics.mir.segment.parser.MigecSegmentLibraryUtils.getLibraryFromResources;

public class MuruganConverterUtilsTest {
    @Test
    public void createTest()  {
        getConverter(
                getModelFromResources(Species.Human, Gene.TRA),
                getLibraryFromResources(Species.Human, Gene.TRA)
        );

        getConverter(
                getModelFromResources(Species.Human, Gene.TRB),
                getLibraryFromResources(Species.Human, Gene.TRB)
        );

        getConverter(
                getModelFromResources(Species.Human, Gene.IGH),
                getLibraryFromResources(Species.Human, Gene.IGH)
        );

        getConverter(
                getModelFromResources(Species.Mouse, Gene.TRB),
                getLibraryFromResources(Species.Mouse, Gene.TRB)
        );
    }

    @Test
    public void builtInLibraryCreateTest() {
        getConverter(
                getModelFromResources(Species.Human, Gene.TRA)
        );

        getConverter(
                getModelFromResources(Species.Human, Gene.TRB)
        );

        getConverter(
                getModelFromResources(Species.Human, Gene.IGH)
        );

        getConverter(
                getModelFromResources(Species.Mouse, Gene.TRB)
        );
    }
}
