package com.milaboratory.mir.structure.output;

import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import com.milaboratory.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.milaboratory.mir.structure.TestStructureCache;
import com.milaboratory.mir.structure.mapper.PeptideMhcComplexMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class GeneralAnnotationWriterTest {

    @Test
    public void test() throws IOException {
        var os = new ByteArrayOutputStream();
        try(var writer = new GeneralAnnotationWriter(os)) {
            var mapper = new PeptideMhcComplexMapper(
                    new SimpleExhaustiveMapperFactory<>(
                            AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62))
            );
            writer.accept(mapper.map(TestStructureCache.get("1ao7")).get());
        }

        System.out.println(os.toString());

        Assert.assertEquals("id\tspecies\tchain\torder\ttype\tallele\n" +
                "1ao7_al\tHuman\tC\t0\tP\tNA\n" +
                "1ao7_al\tHuman\tA\t0\tMHC\tHLA-A2\n" +
                "1ao7_al\tHuman\tB\t1\tMHC\tB2M\n" +
                "1ao7_al\tHuman\tD\t0\tTCR\tTRAV12-2*01:TRAJ24*01\n" +
                "1ao7_al\tHuman\tE\t1\tTCR\tTRBV6-5*01:TRBJ2-7*01\n", os.toString());
    }
}