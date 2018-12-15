package com.milaboratory.mir.structure.output;

import com.milaboratory.mir.structure.TestStructureCache;
import com.milaboratory.mir.structure.pdb.contacts.StructurePairwiseDistances;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CaDistanceWriterTest {
    @Test
    public void test() throws IOException {
        var os = new ByteArrayOutputStream();
        try (var writer = new CaDistanceWriter(os)) {
            var contacts = new StructurePairwiseDistances(TestStructureCache.get("1ao7"));
            writer.accept(contacts);
        }

        //System.out.println(os.toString());
        String res = os.toString();
        //System.out.println(res);
        Assert.assertTrue(!res.contains("\n\n"));
        System.out.println(os.toString().substring(0, 600) + "...");
    }
}