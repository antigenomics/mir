package com.antigenomics.mir.mhc;

import com.antigenomics.mir.Species;

import java.util.*;
import java.util.stream.Collectors;

public enum MhcClassType {
    MHCI("MHCI", Arrays.asList("mhci", "mhc1")),
    MHCII("MHCII", Arrays.asList("mhcii", "mhc2")),
    CHIMERIC("?", Collections.singletonList("?"));

    private final String code;
    private final Set<String> aliases;

    MhcClassType(String code, List<String> aliases) {
        this.code = code;
        this.aliases = Set.copyOf(aliases.stream().map(String::toLowerCase).collect(Collectors.toSet()));
    }

    public String getCode() {
        return code;
    }

    public Set<String> getAliases() {
        return aliases;
    }

    public boolean matches(String alias) {
        return aliases.contains(alias.toLowerCase());
    }

    public static MhcClassType combine(MhcClassType a, MhcClassType b) {
        return a == b ? a : CHIMERIC;
    }

    public static MhcClassType guess(String name) {
        for (MhcClassType mhcClassType : MhcClassType.values()) {
            if (mhcClassType.matches(name)) {
                return mhcClassType;
            }
        }
        throw new IllegalArgumentException("No MHC class type matched name '" + name + "'.");
    }
}
