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
            cache.put("2oi9",
                    PdbParserUtils.parseStructure("2oi9_al", TestUtils.streamFrom("structures/2oi9_al.pdb"))
            );
            cache.put("2pxy",
                    PdbParserUtils.parseStructure("2pxy_al", TestUtils.streamFrom("structures/2pxy_al.pdb"))
            );
            cache.put("4ozh",
                    PdbParserUtils.parseStructure("4ozh_al", TestUtils.streamFrom("structures/4ozh_al.pdb"))
            );
            cache.put("3mbe",
                    PdbParserUtils.parseStructure("3mbe_al", TestUtils.streamFrom("structures/3mbe_al.pdb"))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Structure get(String id) {
        return cache.get(id);
    }
}
