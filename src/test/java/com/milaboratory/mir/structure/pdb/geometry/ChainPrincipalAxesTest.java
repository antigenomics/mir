package com.milaboratory.mir.structure.pdb.geometry;

import com.milaboratory.mir.structure.TestStructureCache;
import com.milaboratory.mir.structure.pdb.Chain;
import org.junit.Test;

public class ChainPrincipalAxesTest {
    @Test
    public void test() {
        for (Chain chain : TestStructureCache.get("1ao7").getChains()) {
            System.out.println(new ChainPrincipalAxes(chain));
        }
    }
}