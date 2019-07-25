package com.antigenomics.mir;

import java.util.*;
import java.util.stream.Collectors;

public enum Species {
    Human("HSA", "HomoSapiens", Arrays.asList("human", "homosapiens", "hsa")),
    Mouse("MMU", "MusMusculus", Arrays.asList("mouse", "musmusculus", "mmu")),
    Monkey("MAMU", "MacacaMulatta", Arrays.asList("monkey", "macacamulatta", "mamu")),
    Mixed("?", "?", Collections.singletonList("?"));

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

    public static Species combine(Species... species) {
        var speciesSet = new HashSet<>(Arrays.asList(species));
        return speciesSet.size() > 1 ? Mixed : speciesSet.iterator().next();
    }

    public static Species guess(String name) {
        for (Species species : Species.values()) {
            if (species.matches(name)) {
                return species;
            }
        }
        throw new IllegalArgumentException("No gene matched name '" + name + "'.");
    }
}
