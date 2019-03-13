package com.antigenomics.mir.structure.output;

import com.antigenomics.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.antigenomics.mir.structure.TestStructureCache;
import com.antigenomics.mir.structure.mapper.PeptideMhcComplexMapper;
import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GeneralAnnotationWriterTest {

    @Test
    public void test() throws IOException {
        var os = new ByteArrayOutputStream();
        try (var writer = new GeneralAnnotationWriter(os)) {
            var mapper = new PeptideMhcComplexMapper(
                    new SimpleExhaustiveMapperFactory<>(
                            AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62))
            );
            writer.accept(mapper.map(TestStructureCache.get("1ao7")).get());
        }

        System.out.println(os.toString());

        Assert.assertEquals(
                "pdb.id\tcomplex.species\tchain.component\tchain.supertype\tchain.type\tchain.id\tallele.info\tseq.length\n" +
                        "1ao7_al\tHuman\tTCR\tTRAB\tTRA\tD\tTRAV12-2*01:TRAJ24*01\t115\n" +
                        "1ao7_al\tHuman\tTCR\tTRAB\tTRB\tE\tTRBV6-5*01:TRBJ2-7*01\t209\n" +
                        "1ao7_al\tHuman\tPEPTIDE\tPEPTIDE\tPEPTIDE\tC\tLLFGYPVYV\t9\n" +
                        "1ao7_al\tHuman\tMHC\tMHCI\tMHCa\tA\tHLA-A2\t274\n" +
                        "1ao7_al\tHuman\tMHC\tMHCI\tMHCb\tB\tB2M\t100\n",
                os.toString());
    }
}