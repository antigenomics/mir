package com.antigenomics.mir.structure.output;

import com.antigenomics.mir.structure.TestStructureCache;
import com.antigenomics.mir.structure.pdb.geometry.summary.StructureAxesAndTorsions;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PrincipalAxesWriterTest {
    @Test
    public void test() throws IOException {
        var os = new ByteArrayOutputStream();
        try (var writer = new PrincipalAxesWriter(os)) {
            var geom = new StructureAxesAndTorsions(TestStructureCache.get("1ao7"));
            writer.accept(geom);
        }

        System.out.println(os.toString());
    }
}