package com.milaboratory.mir.structure.pdb.geometry;

import com.milaboratory.mir.structure.TestStructureCache;
import com.milaboratory.mir.structure.pdb.Residue;
import com.milaboratory.mir.structure.pdb.geometry.summary.ResidueBackbone;
import org.junit.Test;

public class ResidueBackboneTest {
    @Test
    public void test() {
        Residue res = TestStructureCache.get("1ao7").getChain('A').getResidue(50);
        System.out.println(res);
        System.out.println(new ResidueBackbone(res));
    }
}