package com.milaboratory.mir.structure.pdb.geometry;

import com.milaboratory.mir.structure.TestStructureCache;
import com.milaboratory.mir.structure.pdb.Chain;
import com.milaboratory.mir.structure.pdb.geometry.summary.ChainPrincipalAxes;
import org.junit.Assert;
import org.junit.Test;

public class ChainPrincipalAxesTest {
    @Test
    public void test() {
        boolean first = true;
        for (String cacheId : TestStructureCache.getNames()) {
            for (Chain chain : TestStructureCache.get(cacheId).getChains()) {
                ChainPrincipalAxes cpr = new ChainPrincipalAxes(chain);
                if (first) {
                    System.out.println(cpr);
                    first = false;
                }

                Vector3 pr1 = cpr.getPrincipalAxes().getPrincipalAxis1(),
                        pr2 = cpr.getPrincipalAxes().getPrincipalAxis2(),
                        pr3 = cpr.getPrincipalAxes().getPrincipalAxis3();

                Assert.assertEquals(1.0, GeometryUtils.norm(pr1), 1e-6);
                Assert.assertEquals(1.0, GeometryUtils.norm(pr2), 1e-6);
                Assert.assertEquals(1.0, GeometryUtils.norm(pr3), 1e-6);

                Assert.assertEquals(0.0, GeometryUtils.scalarProduct(pr1, pr2), 1e-6);
                Assert.assertEquals(0.0, GeometryUtils.scalarProduct(pr1, pr3), 1e-6);
                Assert.assertEquals(0.0, GeometryUtils.scalarProduct(pr2, pr3), 1e-6);
            }
        }
    }
}