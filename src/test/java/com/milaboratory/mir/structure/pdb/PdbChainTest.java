package com.milaboratory.mir.structure.pdb;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.mir.TestUtils;
import com.milaboratory.mir.mappers.markup.DummyRegion;
import com.milaboratory.mir.mappers.markup.SequenceRegion;
import com.milaboratory.mir.structure.pdb.parser.PdbParserUtils;
import org.junit.Test;

import java.io.IOException;

public class PdbChainTest {
    @Test
    public void getRegionTest() throws IOException {
        var stream = TestUtils.streamFrom("structures/1ao7_al.pdb");
        var struct = PdbParserUtils.parseStructure(stream);
        System.out.println(struct.getChain('A')
                .getRegion(new SequenceRegion<AminoAcidSequence, DummyRegion>(DummyRegion.R1,
                        new AminoAcidSequence("GSHSMRY"),
                        0 , 7)));
    }
}