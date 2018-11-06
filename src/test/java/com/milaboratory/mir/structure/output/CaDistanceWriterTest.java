package com.milaboratory.mir.structure.output;

import com.milaboratory.mir.structure.TestStructureCache;
import com.milaboratory.mir.structure.pdb.contacts.StructurePairwiseDistances;
import com.milaboratory.mir.structure.pdb.geometry.StructureAxesAndTorsions;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class CaDistanceWriterTest {
    @Test
    public void test() throws IOException {
        var os = new ByteArrayOutputStream();
        try (var writer = new CaDistanceWriter(os)) {
            var contacts = new StructurePairwiseDistances(TestStructureCache.get("1ao7"));
            writer.accept(contacts);
        }

        //System.out.println(os.toString());
        System.out.println(os.toString().substring(0, 600) + "...");
    }
}