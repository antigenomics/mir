package com.antigenomics.mir.segment;

import com.antigenomics.mir.Species;

import java.util.*;

public final class SegmentLibraryBundle<T extends SegmentLibrary> {
    private final Map<SpeciesGeneTuple, T> segmentLibraries;

    public SegmentLibraryBundle(Map<SpeciesGeneTuple, T> segmentLibraries) {
        this.segmentLibraries = segmentLibraries;
    }

    public T get(SpeciesGeneTuple speciesGeneTuple) {
        var res = segmentLibraries.get(speciesGeneTuple);
        if (res == null) {
            throw new IllegalArgumentException("No library exists for " + speciesGeneTuple);
        }
        return res;
    }

    public T get(Species species, Gene gene) {
        return get(new SpeciesGeneTuple(species, gene));
    }

    public T get(String species, String gene) {
        return get(Species.guess(species), Gene.guess(gene));
    }

    public List<VariableSegment> getAllVariableSegments(boolean majorAlleles) {
        var segments = new ArrayList<VariableSegment>();
        for (T lib : segmentLibraries.values()) {
            segments.addAll(majorAlleles ? lib.getAllVMajor() : lib.getAllV());
        }
        return segments;
    }

    public List<JoiningSegment> getAllJoiningSegments(boolean majorAlleles) {
        var segments = new ArrayList<JoiningSegment>();
        for (T lib : segmentLibraries.values()) {
            segments.addAll(majorAlleles ? lib.getAllJMajor() : lib.getAllJ());
        }
        return segments;
    }
}
