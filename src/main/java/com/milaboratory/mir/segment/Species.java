package com.milaboratory.mir.segment;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public enum Species {
    Human("HSA", "HomoSapiens", Arrays.asList("human", "homosapiens", "hsa")),
    Mouse("MMU", "MusMusculus", Arrays.asList("mouse", "musmusculus", "mmu"));

    private final String code, fullName;
    private final Set<String> aliases;

    Species(String code, String fullName, List<String> aliases) {
        this.code = code;
        this.fullName = fullName;
        this.aliases = Set.copyOf(aliases.stream().map(String::toLowerCase).collect(Collectors.toSet()));
    }

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public Set<String> getAliases() {
        return aliases;
    }

    public boolean matches(String alias) {
        return aliases.contains(alias.toLowerCase());
    }
}
