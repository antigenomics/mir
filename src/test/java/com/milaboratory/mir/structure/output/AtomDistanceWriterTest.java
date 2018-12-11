package com.milaboratory.mir.structure.output;

import com.milaboratory.mir.structure.TestStructureCache;
import com.milaboratory.mir.structure.pdb.contacts.StructurePairwiseDistances;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class AtomDistanceWriterTest {
    @Test
    public void test() throws IOException {
        var os = new ByteArrayOutputStream();
        try (var writer = new AtomDistanceWriter(os)) {
            var contacts = new StructurePairwiseDistances(TestStructureCache.get("1ao7"));
            writer.accept(contacts);
        }

        //System.out.println(os.toString());
        String res = os.toString();
        //System.out.println(res);
        Assert.assertTrue(!res.contains("\n\n"));
        System.out.println(res.substring(0, 600) + "...");
    }
}