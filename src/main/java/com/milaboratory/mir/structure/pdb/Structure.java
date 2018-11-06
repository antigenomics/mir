package com.milaboratory.mir.structure.pdb;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.structure.pdb.parser.RawAtom;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public final class Structure implements Iterable<Chain> {
    private final String id;
    private final Map<Character, Chain> chains;

    public Structure(String id, Map<Character, Map<Short, List<RawAtom>>> rawAtoms) {
        this.id = id;

        if (rawAtoms.isEmpty()) {
            throw new IllegalArgumentException("No atoms in structure");
        }

        this.chains = CommonUtils.map2map(
                rawAtoms,
                Map.Entry::getKey,
                x -> new Chain(x.getKey(), x.getValue())
        );
    }

    public Structure(String id, List<Chain> chains) {
        this.id = id;
        if (chains.isEmpty()) {
            throw new IllegalArgumentException("No atoms in structure");
        }
        this.chains = chains.stream().collect(Collectors.toMap(Chain::getChainIdentifier, x -> x));
    }

    public String getId() {
        return id;
    }

    public Chain getChain(char chainId) {
        var res = chains.get(chainId);
        if (res == null) {
            throw new IllegalArgumentException("Chain '" + chainId + "' is missing in structure.");
        }
        return res;
    }

    public Set<Character> getChainIdentifiers() {
        return Collections.unmodifiableSet(chains.keySet());
    }

    public Collection<Chain> getChains() {
        return Collections.unmodifiableCollection(chains.values());
    }

    @Override
    public String toString() {
        return chains
                .values()
                .stream()
                .map(Chain::toString).collect(Collectors.joining("\n"));
    }

    @NotNull
    @Override
    public Iterator<Chain> iterator() {
        return chains.values().iterator();
    }
}
