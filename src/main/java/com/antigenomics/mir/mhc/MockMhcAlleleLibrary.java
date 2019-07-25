package com.antigenomics.mir.mhc;

import com.antigenomics.mir.Species;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class MockMhcAlleleLibrary implements MhcAlleleLibrary<MhcAlleleImpl> {
    private final MhcClassType mhcClass;
    private final Species species;
    private final Map<String, MhcAlleleImpl> alleleMap = new ConcurrentHashMap<>();

    public MockMhcAlleleLibrary(MhcClassType mhcClass,
                                Species species) {
        this.mhcClass = mhcClass;
        this.species = species;
    }

    @Override
    public MhcAlleleImpl getAllele(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MhcAlleleImpl getAllele(String id, MhcChainType mhcChainType) {
        return alleleMap.computeIfAbsent(id, x -> new MhcAlleleImpl(id, mhcChainType, mhcClass, species));
    }

    @Override
    public MhcClassType getMhcClass() {
        return mhcClass;
    }

    @Override
    public Species getSpecies() {
        return species;
    }

    @Override
    public Collection<MhcAlleleImpl> getAlleles() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return species + "\t" + mhcClass + ":\n" +
                alleleMap.values().stream().limit(10).map(MhcAlleleImpl::toString).collect(Collectors.joining("\n"));
    }
}
