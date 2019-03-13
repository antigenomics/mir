package com.antigenomics.mir.mhc;

import com.antigenomics.mir.segment.Species;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public final class MhcAlleleLibrary {
    private final MhcClassType mhcClass;
    private final Species species;
    private final Map<String, MhcAllele> alleleMap;

    public MhcAlleleLibrary(MhcClassType mhcClass,
                            Species species,
                            Map<String, MhcAllele> alleleMap) {
        this.mhcClass = mhcClass;
        this.species = species;
        this.alleleMap = alleleMap;

        if (alleleMap.isEmpty()) {
            throw new IllegalArgumentException("Empty allele map");
        }
    }

    public MhcAllele getAllele(String id) {
        return alleleMap.get(id);
    }

    public MhcClassType getMhcClass() {
        return mhcClass;
    }

    public Species getSpecies() {
        return species;
    }

    public Collection<MhcAllele> getAlleles() {
        return Collections.unmodifiableCollection(alleleMap.values());
    }

    @Override
    public String toString() {
        return species + "\t" + mhcClass + ":\n" +
                alleleMap.values().stream().limit(10).map(MhcAllele::toString).collect(Collectors.joining("\n"));
    }
}