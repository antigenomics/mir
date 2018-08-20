package com.milaboratory.mir.segment;

import java.util.*;
import java.util.stream.Collectors;

public enum SegmentType {
    V("V", "Variable", Arrays.asList("v", "variable", "trav", "trbv")), // todo
    D("D", "Diversity", Arrays.asList("d", "diversity")),
    J("J", "Joining", Arrays.asList("j", "joining")),
    C("C", "Constant", Arrays.asList("c", "contstant"));

    private final static Map<String, SegmentType> aliasMap = new HashMap<>();

    static {
        Arrays.stream(values()).forEach(st -> st.getAliases().forEach(alias -> aliasMap.put(alias.toLowerCase(), st)));
    }

    private final String code, fullName;
    private final Set<String> aliases;

    SegmentType(String code, String fullName, List<String> aliases) {
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

    public static SegmentType byAlias(String alias) {
        return aliasMap.get(alias.toLowerCase());
    }
}
