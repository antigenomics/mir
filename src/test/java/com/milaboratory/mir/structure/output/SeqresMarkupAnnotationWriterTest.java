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

public class SeqresMarkupAnnotationWriterTest {
    @Test
    public void test() throws IOException {
        var mapper = new PeptideMhcComplexMapper(
                new SimpleExhaustiveMapperFactory<>(
                        AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62))
        );
        var mappingRes = mapper.map(TestStructureCache.get("1ao7")).get();

        var os = new ByteArrayOutputStream();
        try (var writer = new SeqresMarkupAnnotationWriter(os)) {

            writer.accept(mappingRes);
        }

        var res = os.toString();

        //System.out.println(res);
        System.out.println(res.substring(0, 600) + "...");

        Assert.assertEquals(606, res.split("\n").length);

        Assert.assertTrue(res.contains("1ao7_al\tD\tCDR3\t87\t90\t \tC\n" +
                "1ao7_al\tD\tCDR3\t88\t91\t \tA\n" +
                "1ao7_al\tD\tCDR3\t89\t92\t \tV\n" +
                "1ao7_al\tD\tCDR3\t90\t93\t \tT\n" +
                "1ao7_al\tD\tCDR3\t91\t98\t \tT\n" +
                "1ao7_al\tD\tCDR3\t92\t99\t \tD\n" +
                "1ao7_al\tD\tCDR3\t93\t100\t \tS\n" +
                "1ao7_al\tD\tCDR3\t94\t101\t \tW\n" +
                "1ao7_al\tD\tCDR3\t95\t102\t \tG\n" +
                "1ao7_al\tD\tCDR3\t96\t103\t \tK\n" +
                "1ao7_al\tD\tCDR3\t97\t104\t \tL\n" +
                "1ao7_al\tD\tCDR3\t98\t105\t \tQ\n" +
                "1ao7_al\tD\tCDR3\t99\t106\t \tF"));
    }
}