package com.milaboratory.mir.structure.pdb.geometry;

import com.milaboratory.mir.structure.TestStructureCache;
import com.milaboratory.mir.structure.pdb.Chain;
import com.milaboratory.mir.structure.pdb.Residue;
import org.junit.Assert;
import org.junit.Test;

public class ResidueTorsionAnglesTest {
    @Test
    public void test() {
        Chain chain = TestStructureCache.get("1ao7").getChain('A');
        Residue res1 = chain.getResidue(49),
                res2 = chain.getResidue(50),
                res3 = chain.getResidue(51),
                res4 = chain.getResidue(52);

        // omega should be near 3.14
        System.out.println(new ResidueTorsionAngles(res1, res2, res3));
        System.out.println(new ResidueTorsionAngles(res2, res3, res4));
    }

    @Test
    public void test2() {
        Chain chain = TestStructureCache.get("1ao7").getChain('B');
        Residue res1 = chain.getResidue(74),
                res2 = chain.getResidue(75),
                res3 = chain.getResidue(76);

        ResidueTorsionAngles x = new ResidueTorsionAngles(res1, res2, res3);
        Assert.assertTrue(!Float.isNaN(x.getOmega()));
    }
}