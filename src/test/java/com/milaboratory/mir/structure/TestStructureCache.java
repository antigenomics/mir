package com.milaboratory.mir.structure;

import com.milaboratory.mir.TestUtils;
import com.milaboratory.mir.structure.pdb.Structure;
import com.milaboratory.mir.structure.pdb.parser.PdbParserUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestStructureCache {
    private static final Map<String, Structure> cache = new HashMap<>();

    static {
        try {
            cache.put("1ao7",
                    PdbParserUtils.parseStructure("1ao7_al", TestUtils.streamFrom("structures/1ao7_al.pdb"))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Structure get(String id) {
        return cache.get(id);
    }
}
