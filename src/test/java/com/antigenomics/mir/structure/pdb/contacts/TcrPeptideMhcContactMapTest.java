package com.antigenomics.mir.structure.pdb.contacts;

import com.antigenomics.mir.structure.mapper.PeptideMhcComplexMapperTest;
import org.junit.Test;

public class TcrPeptideMhcContactMapTest {

    @Test
    public void test() {
        var res1 = PeptideMhcComplexMapperTest.map("2oi9").get();
        var res2 = PeptideMhcComplexMapperTest.map("4ozh").get();

        new TcrPeptideMhcContactMap(res1, res2);

        //System.out.println("zzz");
    }
}