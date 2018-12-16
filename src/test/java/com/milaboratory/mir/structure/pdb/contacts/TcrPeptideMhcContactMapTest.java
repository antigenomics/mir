package com.milaboratory.mir.structure.pdb.contacts;

import com.milaboratory.mir.structure.mapper.PeptideMhcComplexMapperTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class TcrPeptideMhcContactMapTest {

    @Test
    public void test() {
        var res1 = PeptideMhcComplexMapperTest.map("2oi9").get();
        var res2 = PeptideMhcComplexMapperTest.map("4ozh").get();

        new TcrPeptideMhcContactMap(res1, res2);

        //System.out.println("zzz");
    }
}