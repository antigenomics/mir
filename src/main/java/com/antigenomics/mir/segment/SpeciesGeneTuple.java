package com.antigenomics.mir.segment;

import com.antigenomics.mir.Species;

import java.util.Objects;

public final class SpeciesGeneTuple {
    private final Species species;
    private final Gene gene;

    public SpeciesGeneTuple(Species species, Gene gene) {
        this.species = species;
        this.gene = gene;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpeciesGeneTuple that = (SpeciesGeneTuple) o;
        return species == that.species &&
                gene == that.gene;
    }

    @Override
    public int hashCode() {
        return Objects.hash(species, gene);
    }

    @Override
    public String toString() {
        return species + " " + gene;
    }
}
