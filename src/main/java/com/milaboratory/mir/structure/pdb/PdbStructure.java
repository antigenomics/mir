package com.milaboratory.mir.structure.pdb;

import java.util.*;
import java.util.stream.Collectors;

public class PdbStructure {
    private final Map<Character, PdbChain> chains;

    public PdbStructure(List<Atom> atoms) {
        var rawChains = new HashMap<Character, List<Atom>>();
        for (Atom atom : atoms) {
            rawChains.computeIfAbsent(atom.getChainIdentifier(), x -> new ArrayList<>()).add(atom);
        }
        this.chains = new HashMap<>();
        rawChains.forEach((k, v) -> chains.put(k, new PdbChain(k, v)));
    }

    public PdbChain getChain(char chainId) {
        var res = chains.get(chainId);
        if (res == null) {
            throw new IllegalArgumentException("Chain " + chainId + " doesn't exist in structure");
        }
        return res;
    }

    public Collection<PdbChain> getChains() {
        return Collections.unmodifiableCollection(chains.values());
    }

    public Set<Character> getChainIdentifiers() {
        return Collections.unmodifiableSet(chains.keySet());
    }

    @Override
    public String toString() {
        return chains.values().stream().map(PdbChain::toString).collect(Collectors.joining("\n"));
    }
}
