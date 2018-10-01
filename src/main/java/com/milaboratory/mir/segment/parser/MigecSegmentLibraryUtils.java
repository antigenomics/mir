package com.milaboratory.mir.segment.parser;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.segment.SegmentLibraryImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class MigecSegmentLibraryUtils {
    public static final String PATH = "segments.txt";

    private static final Map<SpeciesGene, SegmentLibraryImpl> resourceLibraryCache = new ConcurrentHashMap<>();

    private MigecSegmentLibraryUtils() {

    }

    public static SegmentLibraryImpl getLibraryFromResources(Species species, Gene gene) {
        return resourceLibraryCache.computeIfAbsent(
                new SpeciesGene(species, gene),
                x -> {
                    try {
                        return MigecSegmentLibraryParser.load(CommonUtils.getResourceAsStream(PATH), species, gene);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public static SegmentLibraryImpl getLibraryFromFile(String path, Species species, Gene gene) throws IOException {
        return MigecSegmentLibraryParser.load(new FileInputStream(path), species, gene);
    }

    private static final class SpeciesGene {
        final Species species;
        final Gene gene;

        public SpeciesGene(Species species, Gene gene) {
            this.species = species;
            this.gene = gene;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SpeciesGene that = (SpeciesGene) o;
            return species == that.species &&
                    gene == that.gene;
        }

        @Override
        public int hashCode() {
            return Objects.hash(species, gene);
        }
    }
}
