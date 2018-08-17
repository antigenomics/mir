package com.milaboratory.mir.segment;

import com.milaboratory.mir.Gene;
import com.milaboratory.mir.Species;

import java.util.Map;

public class SegmentLibraryImpl implements SegmentLibrary {
    private final Species species;
    private final Gene gene;
    private final Map<String, VariableSegment> variableSegmentMap;
    private final Map<String, DiversitySegment> diversitySegmentMap;
    private final Map<String, JoiningSegment> joiningSegmentMap;
    private final Map<String, ConstantSegment> constantSegmentMap;

    public SegmentLibraryImpl(Species species, Gene gene,
                              Map<String, VariableSegment> variableSegmentMap,
                              Map<String, DiversitySegment> diversitySegmentMap,
                              Map<String, JoiningSegment> joiningSegmentMap,
                              Map<String, ConstantSegment> constantSegmentMap) {
        this.species = species;
        this.gene = gene;
        this.variableSegmentMap = variableSegmentMap;
        this.diversitySegmentMap = diversitySegmentMap;
        this.joiningSegmentMap = joiningSegmentMap;
        this.constantSegmentMap = constantSegmentMap;
    }

    @Override
    public VariableSegment getOrCreateV(String id) {
        return variableSegmentMap.getOrDefault(id, MissingVariableSegment.INSTANCE);
    }

    @Override
    public JoiningSegment getOrCreateJ(String id) {
        return joiningSegmentMap.getOrDefault(id, MissingJoiningSegment.INSTANCE);
    }

    @Override
    public DiversitySegment getOrCreateD(String id) {
        return diversitySegmentMap.getOrDefault(id, MissingDiversitySegment.INSTANCE);
    }

    @Override
    public ConstantSegment getOrCreateC(String id) {
        return constantSegmentMap.getOrDefault(id, MissingConstantSegment.INSTANCE);
    }

    @Override
    public Species getSpecies() {
        return species;
    }

    @Override
    public Gene getGene() {
        return gene;
    }
}
