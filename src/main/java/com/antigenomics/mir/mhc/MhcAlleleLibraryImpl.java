package com.antigenomics.mir.mhc;

import com.antigenomics.mir.Species;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public final class MhcAlleleLibraryImpl<T extends MhcAllele> implements MhcAlleleLibrary<T> {
    private final MhcClassType mhcClass;
    private final Species species;
    private final Map<String, T> alleleMap;

    public MhcAlleleLibraryImpl(MhcClassType mhcClass,
                                Species species,
                                Map<String, T> alleleMap) {
        this.mhcClass = mhcClass;
        this.species = species;
        this.alleleMap = alleleMap;

        if (alleleMap.isEmpty()) {
            throw new IllegalArgumentException("Empty allele map");
        }
    }

    @Override
    public T getAllele(String id) {
        var res = alleleMap.get(id);
        if (res == null) {
            throw new IllegalArgumentException("MHC allele " + id + " is not in the library.");
        }
        return res;
    }

    @Override
    public T getAllele(String id, MhcChainType mhcChainType) {
        var res = getAllele(id);
        if (res.getMhcChainType() != mhcChainType) {
            throw new IllegalArgumentException("MHC chain type " + mhcChainType +
                    " don't match with the type in the library.");
        }
        return res;
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
    public Collection<T> getAlleles() {
        return Collections.unmodifiableCollection(alleleMap.values());
    }

    @Override
    public String toString() {
        return species + "\t" + mhcClass + ":\n" +
                alleleMap.values().stream().limit(10).map(T::toString).collect(Collectors.joining("\n"));
    }
}
