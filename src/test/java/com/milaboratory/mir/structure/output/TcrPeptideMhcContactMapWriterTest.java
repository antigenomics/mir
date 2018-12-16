package com.milaboratory.mir.structure.output;

import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import com.milaboratory.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.milaboratory.mir.structure.TestStructureCache;
import com.milaboratory.mir.structure.mapper.PeptideMhcComplexMapper;
import com.milaboratory.mir.structure.pdb.contacts.TcrPeptideMhcContactMap;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class TcrPeptideMhcContactMapWriterTest {

    @Test
    public void convert() throws IOException {
        var os = new ByteArrayOutputStream();
        try (var writer = new TcrPeptideMhcContactMapWriter(os)) {
            var mapper = new PeptideMhcComplexMapper(
                    new SimpleExhaustiveMapperFactory<>(
                            AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62))
            );
            writer.accept(new TcrPeptideMhcContactMap(mapper.map(TestStructureCache.get("1ao7")).get()));
        }

        var res = os.toString();
        Assert.assertTrue(res.contains("CDR3\tPEPTIDE"));
        //System.out.println(os.toString());
        System.out.println(res.substring(0, 600) + "...");
    }
}