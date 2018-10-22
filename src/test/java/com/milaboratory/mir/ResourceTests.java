package com.milaboratory.mir;

import org.junit.Test;

import java.io.IOException;

public class ResourceTests {
    @Test
    public void testTestResources() throws IOException {
//        TestUtils.testStream(CommonUtils.getResourceAsStream("samples/trad_sample.txt"));
        TestUtils.testStream(CommonUtils.getResourceAsStream("samples/trad_sample.txt.gz"));
        TestUtils.testStream(CommonUtils.getResourceAsStream("structures/1ao7_al.pdb"));
        //todo
    }

    @Test
    public void testMainResources() throws IOException {
        TestUtils.testStream(CommonUtils.getResourceAsStream("segments.txt"));
        TestUtils.testStream(CommonUtils.getResourceAsStream("murugan_models/hsa_tra_params.txt"));
        TestUtils.testStream(CommonUtils.getResourceAsStream("murugan_models/hsa_tra_marginals.txt"));
        TestUtils.testStream(CommonUtils.getResourceAsStream("murugan_models/hsa_trb_params.txt"));
        TestUtils.testStream(CommonUtils.getResourceAsStream("murugan_models/hsa_trb_marginals.txt"));
        TestUtils.testStream(CommonUtils.getResourceAsStream("murugan_models/mmu_trb_params.txt"));
        TestUtils.testStream(CommonUtils.getResourceAsStream("murugan_models/mmu_trb_marginals.txt"));

        //todo
    }
}
