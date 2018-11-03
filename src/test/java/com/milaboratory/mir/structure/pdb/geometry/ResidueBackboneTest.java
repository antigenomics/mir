package com.milaboratory.mir.structure.pdb.geometry;

import com.milaboratory.mir.structure.TestStructureCache;
import com.milaboratory.mir.structure.pdb.Residue;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResidueBackboneTest {
    @Test
    public void test() {
        Residue res = TestStructureCache.get("1ao7").getChain('A').getResidue(50);
        System.out.println(res);
        System.out.println(new ResidueBackbone(res));
    }
}