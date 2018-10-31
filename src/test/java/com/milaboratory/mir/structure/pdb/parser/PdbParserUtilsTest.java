package com.milaboratory.mir.structure.pdb.parser;

import com.milaboratory.mir.TestUtils;
import org.biojava.nbio.structure.io.PDBFileParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class PdbParserUtilsTest {
    @Test
    public void parseAtomTest() {
        var line = "TER    5680      ASP E 246                                                      ";
        RawAtom atom = PdbParserUtils.parseAtom(line);

        String actual = PdbParserUtils.writeAtom(atom);
        System.out.println(actual);
        Assert.assertEquals(line, actual);
    }

    @Test
    public void parseAtomTest2() {
        var line = "ATOM   5631  OE2 GLU E 240     114.615 292.086  -9.668  1.00 65.69           O1-";
        RawAtom atom = PdbParserUtils.parseAtom(line);

        String actual = PdbParserUtils.writeAtom(atom);
        System.out.println(actual);
        Assert.assertEquals(line, actual);
    }

    @Test
    public void parseStructureTest() throws IOException {
        var stream = TestUtils.streamFrom("structures/1ao7_al.pdb");
        var struct = PdbParserUtils.parseStructure("1ao7_al", stream);
        System.out.println(struct);
        Assert.assertEquals(5, struct.getChains().size());
        Assert.assertEquals("1ao7_al", struct.getId());
    }

    // todo
    @Test
    public void parseStructSpeedTest() throws IOException {
        var streamProvider = TestUtils.streamSupplierFrom("structures/1ao7_al.pdb");
        int n = 10;
        long start, end;

        start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            PdbParserUtils.parseStructure("1ao7_al", streamProvider.get());
        }
        end = System.currentTimeMillis();
        System.out.println("We read " + n + " structures in " + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            new PDBFileParser().parsePDBFile(streamProvider.get());
        }
        end = System.currentTimeMillis();
        System.out.println("BioJava reads " + n + " structures in " + (end - start) + "ms");
    }

    @Test
    public void parseTestAdditional1() throws IOException {
        PdbParserUtils.parseStructure("2pxy_al", TestUtils.streamFrom("structures/2pxy_al.pdb"));
    }
}
