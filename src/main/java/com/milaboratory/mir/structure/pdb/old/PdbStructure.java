package com.milaboratory.mir.structure.pdb.old;

import com.milaboratory.mir.structure.pdb.parser.RawAtom;

import java.util.*;
import java.util.stream.Collectors;

public class PdbStructure {
    private final Map<Character, OldPdbChain> chains;

    public PdbStructure(List<RawAtom> atoms) {
        var rawChains = new HashMap<Character, List<RawAtom>>();
        for (RawAtom atom : atoms) {
            rawChains.computeIfAbsent(atom.getChainIdentifier(), x -> new ArrayList<>()).add(atom);
        }
        this.chains = new HashMap<>();
        rawChains.forEach((k, v) -> chains.put(k, new OldPdbChain(k, v)));
    }

    public OldPdbChain getChain(char chainId) {
        var res = chains.get(chainId);
        if (res == null) {
            throw new IllegalArgumentException("Chain " + chainId + " doesn't exist in structure");
        }
        return res;
    }

    public Collection<OldPdbChain> getChains() {
        return Collections.unmodifiableCollection(chains.values());
    }

    public Set<Character> getChainIdentifiers() {
        return Collections.unmodifiableSet(chains.keySet());
    }

    @Override
    public String toString() {
        return chains.values().stream()
                .map(OldPdbChain::toString).collect(Collectors.joining("\n"));
    }
}
