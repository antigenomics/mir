package com.milaboratory.mir.segment;

import java.util.*;
import java.util.stream.Collectors;

public class SegmentLibraryImpl implements SegmentLibrary {
    private final Species species;
    private final Gene gene;
    private final Map<String, VariableSegment> variableSegmentMap, variableSegmentMajorAlleleMap;
    private final Map<String, DiversitySegment> diversitySegmentMap, diversitySegmentMajorAlleleMap;
    private final Map<String, JoiningSegment> joiningSegmentMap, joiningSegmentMajorAlleleMap;
    private final Map<String, ConstantSegment> constantSegmentMap, constantSegmentMajorAlleleMap;

    public SegmentLibraryImpl(Species species, Gene gene,
                              Map<String, VariableSegment> variableSegmentMap,
                              Map<String, DiversitySegment> diversitySegmentMap,
                              Map<String, JoiningSegment> joiningSegmentMap,
                              Map<String, ConstantSegment> constantSegmentMap) {
        this(species, gene, variableSegmentMap, diversitySegmentMap, joiningSegmentMap, constantSegmentMap,
                createMockMajorMap(variableSegmentMap, diversitySegmentMap, joiningSegmentMap, constantSegmentMap));
    }

    private static Map<String, String> createMockMajorMap(Map<String, ? extends Segment> vMap,
                                                          Map<String, ? extends Segment> dMap,
                                                          Map<String, ? extends Segment> jMap,
                                                          Map<String, ? extends Segment> cMap) {
        var majorMap = new HashMap<String, String>();

        majorMap.putAll(createMockMajorMap(vMap));
        majorMap.putAll(createMockMajorMap(dMap));
        majorMap.putAll(createMockMajorMap(jMap));
        majorMap.putAll(createMockMajorMap(cMap));

        return majorMap;
    }

    private static Map<String, String> createMockMajorMap(Map<String, ? extends Segment> map){
        var majorMap = new HashMap<String, String>();

        for(String name : map.keySet()) {
            majorMap.put(name, name);
            for (String suffix : Arrays.asList("*00", "*01")) {
                var majorName = name.replaceFirst("\\*\\d\\d$", suffix);
                if (map.containsKey(majorName)) {
                    majorMap.put(name, majorName);
                }
            }
        }

        return majorMap;
    }

    public SegmentLibraryImpl(Species species, Gene gene,
                              Map<String, VariableSegment> variableSegmentMap,
                              Map<String, DiversitySegment> diversitySegmentMap,
                              Map<String, JoiningSegment> joiningSegmentMap,
                              Map<String, ConstantSegment> constantSegmentMap,
                              Map<String, String> majorAlleleAliases) {
        this.species = species;
        this.gene = gene;
        if (variableSegmentMap.isEmpty()) {
            throw new IllegalArgumentException("No Variable segments in library.");
        }
        if (joiningSegmentMap.isEmpty()) {
            throw new IllegalArgumentException("No Joining segments in library.");
        }
        this.variableSegmentMap = variableSegmentMap;
        this.diversitySegmentMap = diversitySegmentMap;
        this.joiningSegmentMap = joiningSegmentMap;
        this.constantSegmentMap = constantSegmentMap;
        this.variableSegmentMajorAlleleMap = createConversions(variableSegmentMap, majorAlleleAliases);
        this.diversitySegmentMajorAlleleMap = createConversions(diversitySegmentMap, majorAlleleAliases);
        this.joiningSegmentMajorAlleleMap = createConversions(joiningSegmentMap, majorAlleleAliases);
        this.constantSegmentMajorAlleleMap = createConversions(constantSegmentMap, majorAlleleAliases);
    }

    private static <T extends Segment> Map<String, T> createConversions(Map<String, T> allSegments,
                                                                        Map<String, String> majorAlleles) {
        var conversions = new HashMap<String, T>();
        allSegments.forEach((k, v) -> {
                    var majorId = majorAlleles.get(k);
                    if (majorId == null) {
                        throw new IllegalArgumentException("No major allele info provided for '" + k + "'");
                    }
                    var major = allSegments.get(majorId);
                    if (major == null) {
                        throw new IllegalArgumentException("Data for major allele '" + majorId +
                                "' specified for '" + k + "' is not found in library.");
                    }
                    if (!major.isMajorAllele()) {
                        throw new IllegalArgumentException("Allele '" + majorId +
                                "' specified as major is not actually major");
                    }
                    if (v.isMajorAllele() && v != major) {
                        throw new IllegalArgumentException("Major allele '" + k +
                                "' should be itself, while a different allele '" + majorId +
                                "' was returned.");
                    }
                    conversions.put(k, major);
                }
        );
        return conversions;
    }

    @Override
    public VariableSegment getV(String id) {
        return variableSegmentMap.getOrDefault(id, MissingVariableSegment.INSTANCE);
    }

    @Override
    public JoiningSegment getJ(String id) {
        return joiningSegmentMap.getOrDefault(id, MissingJoiningSegment.INSTANCE);
    }

    @Override
    public DiversitySegment getD(String id) {
        return diversitySegmentMap.getOrDefault(id, MissingDiversitySegment.INSTANCE);
    }

    @Override
    public ConstantSegment getC(String id) {
        return constantSegmentMap.getOrDefault(id, MissingConstantSegment.INSTANCE);
    }

    @Override
    public VariableSegment getVMajor(String id) {
        return variableSegmentMajorAlleleMap.getOrDefault(id, MissingVariableSegment.INSTANCE);
    }

    @Override
    public JoiningSegment getJMajor(String id) {
        return joiningSegmentMajorAlleleMap.getOrDefault(id, MissingJoiningSegment.INSTANCE);
    }

    @Override
    public DiversitySegment getDMajor(String id) {
        return diversitySegmentMajorAlleleMap.getOrDefault(id, MissingDiversitySegment.INSTANCE);
    }

    @Override
    public ConstantSegment getCMajor(String id) {
        return constantSegmentMajorAlleleMap.getOrDefault(id, MissingConstantSegment.INSTANCE);
    }

    @Override
    public Collection<VariableSegment> getAllV() {
        return Collections.unmodifiableCollection(variableSegmentMap.values());
    }

    @Override
    public Collection<JoiningSegment> getAllJ() {
        return Collections.unmodifiableCollection(joiningSegmentMap.values());
    }

    @Override
    public Collection<DiversitySegment> getAllD() {
        return Collections.unmodifiableCollection(diversitySegmentMap.values());
    }

    @Override
    public Collection<ConstantSegment> getAllC() {
        return Collections.unmodifiableCollection(constantSegmentMap.values());
    }

    @Override
    public Collection<VariableSegment> getAllVMajor() {
        return Collections.unmodifiableCollection(variableSegmentMajorAlleleMap.values());
    }

    @Override
    public Collection<JoiningSegment> getAllJMajor() {
        return Collections.unmodifiableCollection(joiningSegmentMajorAlleleMap.values());
    }

    @Override
    public Collection<DiversitySegment> getAllDMajor() {
        return Collections.unmodifiableCollection(diversitySegmentMajorAlleleMap.values());
    }

    @Override
    public Collection<ConstantSegment> getAllCMajor() {
        return Collections.unmodifiableCollection(constantSegmentMajorAlleleMap.values());
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
