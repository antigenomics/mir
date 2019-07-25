package com.antigenomics.mir.mhc;

import com.antigenomics.mir.Species;
import com.antigenomics.mir.segment.Gene;

import java.util.Objects;

public final class SpeciesMhcClassTuple {
    private final Species species;
    private final MhcClassType mhcClassType;

    public SpeciesMhcClassTuple(Species species, MhcClassType mhcClassType) {
        this.species = species;
        this.mhcClassType = mhcClassType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpeciesMhcClassTuple that = (SpeciesMhcClassTuple) o;
        return species == that.species &&
                mhcClassType == that.mhcClassType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(species, mhcClassType);
    }

    @Override
    public String toString() {
        return species + " " + mhcClassType;
    }
}
