package com.milaboratory.mir.segment;

import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        String res = "[Segment library for " + species + " " + gene + "]\n";

        res += "> #V segments = " + variableSegmentMap.size() + "\n";
        res += variableSegmentMap.values().stream().limit(10)
                .map(VariableSegment::toString).collect(Collectors.joining("\n"));
        res += "\n> #D segments = " + diversitySegmentMap.size() + "\n";
        res += diversitySegmentMap.values().stream().limit(10)
                .map(DiversitySegment::toString).collect(Collectors.joining("\n"));
        res += "\n> #J segments = " + joiningSegmentMap.size() + "\n";
        res += joiningSegmentMap.values().stream().limit(10)
                .map(JoiningSegment::toString).collect(Collectors.joining("\n"));
        res += "\n> #C segments = " + constantSegmentMap.size() + "\n";
        res += constantSegmentMap.values().stream().limit(10)
                .map(ConstantSegment::toString).collect(Collectors.joining("\n"));
        res += "\n";

        return res;
    }
}
