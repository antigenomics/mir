package com.antigenomics.mir.graph;

import com.antigenomics.mir.clonotype.ClonotypeTable;
import com.antigenomics.mir.pipe.ReproducibleGeneratorPipe;
import com.antigenomics.mir.rearrangement.RearrangementTemplate;
import com.antigenomics.mir.rearrangement.converter.MuruganConverterUtils;
import com.antigenomics.mir.rearrangement.parser.MuruganModelParserUtils;
import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.Species;
import com.antigenomics.mir.segment.MigecSegmentLibraryUtils;
import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import org.junit.Test;

public class Cdr3VJPairwiseDistancesTest {

    @Test
    public void test() {
        var mdl = MuruganConverterUtils.asRearrangementModel(
                MuruganModelParserUtils.getModelFromResources(Species.Human, Gene.TRB),
                MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRB)
        );
        var io = new ReproducibleGeneratorPipe<>(
                () -> ((RearrangementTemplate) mdl.generate()).toRearrangement(),
                100);

        var input = new ClonotypeTable<>(io);

        var scoring = AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62);

        System.out.println(new Cdr3VJPairwiseDistances<>(input, input, scoring, scoring).stream().count());

        //new Cdr3VJPairwiseDistances<>(input, input, scoring, scoring).stream().forEach(System.out::println);
    }

    @Test
    public void testParallel() {
    }
}