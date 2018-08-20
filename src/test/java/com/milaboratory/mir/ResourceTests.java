package com.milaboratory.mir;

import org.junit.Test;

import java.io.IOException;

public class ResourceTests {
    @Test
    public void testResources() throws IOException {
        TestUtils.testStream(CommonUtils.getResourceAsStream("samples/trad_sample.txt"));
        //todo
    }
}
