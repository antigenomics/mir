package com.milaboratory.mir.structure.pdb.parser;

import com.milaboratory.mir.TestUtils;
import com.milaboratory.mir.structure.pdb.Atom;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class PdbParserUtilsTest {
    @Test
    public void parseAtomTest() {
        var line = "ATOM   5631  OE2 GLU E 240     114.615 292.086  -9.668  1.00 65.69           O1-";
        Atom atom = PdbParserUtils.parseAtom(line);

        String actual = PdbParserUtils.writeAtom(atom);
        System.out.println(actual);
        Assert.assertEquals(line, actual);
    }

    @Test
    public void parseStructureTest() throws IOException {
        var stream = TestUtils.streamFrom("structures/1ao7_al.pdb");
        var struct = PdbParserUtils.parseStructure(stream);
        System.out.println(struct);
    }
}
