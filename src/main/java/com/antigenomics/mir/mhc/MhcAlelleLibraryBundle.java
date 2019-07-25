package com.antigenomics.mir.mhc;

import com.antigenomics.mir.Species;

import java.util.*;

public final class MhcAlelleLibraryBundle<V extends MhcAllele, T extends MhcAlleleLibrary<V>> {
    private final Map<SpeciesMhcClassTuple, T> mhcAlleleLibraries;

    public MhcAlelleLibraryBundle(Map<SpeciesMhcClassTuple, T> mhcAlleleLibraries) {
        this.mhcAlleleLibraries = mhcAlleleLibraries;
    }

    public T get(SpeciesMhcClassTuple speciesMhcClassTuple) {
        var res = mhcAlleleLibraries.get(speciesMhcClassTuple);
        if (res == null) {
            throw new IllegalArgumentException("No library exists for " + speciesMhcClassTuple);
        }
        return res;
    }

    public T get(Species species, MhcClassType mhcClassType) {
        return get(new SpeciesMhcClassTuple(species, mhcClassType));
    }

    public T get(String species, String mhcClassType) {
        return get(Species.guess(species), MhcClassType.guess(mhcClassType));
    }

    public List<V> getAllMhcAlleles() {
        var alleles = new ArrayList<V>();
        for (T lib : mhcAlleleLibraries.values()) {
            alleles.addAll(lib.getAlleles());
        }
        return alleles;
    }
}
