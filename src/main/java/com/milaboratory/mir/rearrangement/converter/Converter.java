package com.milaboratory.mir.rearrangement.converter;

import com.milaboratory.mir.probability.parser.PlainTextHierarchicalModel;
import com.milaboratory.mir.probability.parser.PlainTextHierarchicalModelUtils;
import com.milaboratory.mir.rearrangement.blocks.*;
import com.milaboratory.mir.segment.*;

import java.util.Map;

public class Converter<T extends PlainTextHierarchicalModel> {
    private final SegmentLibrary segmentLibrary;
    private final T plainTextHierarchicalModel;

    public Converter(SegmentLibrary segmentLibrary, T plainTextHierarchicalModel) {
        this.segmentLibrary = segmentLibrary;
        this.plainTextHierarchicalModel = plainTextHierarchicalModel;
    }

    protected Map<String, Double> getProbabilities(String blockName) {
        return plainTextHierarchicalModel.getProbabilityMap(blockName);
    }

    protected Map<String, Map<String, Double>> getProbabilities1(String blockName) {
        return PlainTextHierarchicalModelUtils.embed1Conditional(
                plainTextHierarchicalModel.getProbabilityMap(blockName)
        );
    }

    protected Map<String, Map<String, Map<String, Double>>> getProbabilities2(String blockName) {
        return PlainTextHierarchicalModelUtils.embed2Conditional(
                plainTextHierarchicalModel.getProbabilityMap(blockName)
        );
    }

    public VariableDistribution getVariableDistribution(String blockName) {
        return VariableDistribution.fromMap(
                ConverterUtils.convertSegment(
                        getProbabilities(blockName),
                        segmentLibrary,
                        VariableSegment.class
                )
        );
    }

    public DiversityDistribution getDiversityDistribution(String blockName) {
        return DiversityDistribution.fromMap(
                ConverterUtils.convertSegment(
                        getProbabilities(blockName),
                        segmentLibrary,
                        DiversitySegment.class
                )
        );
    }

    public JoiningDistribution getJoiningDistribution(String blockName) {
        return JoiningDistribution.fromMap(
                ConverterUtils.convertSegment(
                        getProbabilities(blockName),
                        segmentLibrary,
                        JoiningSegment.class
                )
        );
    }

    public DiversityJoiningDistribution getDiversityJoiningDistribution(String blockName) {
        return DiversityJoiningDistribution.fromMap(
                ConverterUtils.convertSegment(
                        getProbabilities1(blockName),
                        segmentLibrary,
                        DiversitySegment.class,
                        JoiningSegment.class
                )
        );
    }

    public JoiningVariableDistribution getJoiningVariableDistribution(String blockName) {
        return JoiningVariableDistribution.fromMap(
                ConverterUtils.convertSegment(
                        getProbabilities1(blockName),
                        segmentLibrary,
                        JoiningSegment.class,
                        VariableSegment.class
                )
        );
    }

    public DiversityJoiningVariableDistribution getDiversityJoiningVariableDistribution(String blockName) {
        return DiversityJoiningVariableDistribution.fromMap(
                ConverterUtils.convertSegment(
                        getProbabilities2(blockName),
                        segmentLibrary,
                        DiversitySegment.class,
                        JoiningSegment.class,
                        VariableSegment.class
                )
        );
    }

    public InsertSizeDistribution getInsertSizeDistribution(String blockName) {
        return InsertSizeDistribution.fromMap(
                ConverterUtils.convertIndelSize(
                        getProbabilities(blockName)
                )
        );
    }

    public TrimmingDistribution getTrimmingDistribution(String blockName) {
        return TrimmingDistribution.fromMap(
                ConverterUtils.convertIndelSize(
                        getProbabilities(blockName)
                )
        );
    }

    public VariableTrimmingDistribution getVariableTrimmingDistribution(String blockName) {
        return VariableTrimmingDistribution.fromMap(
                ConverterUtils.convertSegmentTrimming(
                        getProbabilities1(blockName),
                        segmentLibrary,
                        VariableSegment.class
                )
        );
    }

    public JoiningTrimmingDistribution getJoiningTrimmingDistribution(String blockName) {
        return JoiningTrimmingDistribution.fromMap(
                ConverterUtils.convertSegmentTrimming(
                        getProbabilities1(blockName),
                        segmentLibrary,
                        JoiningSegment.class
                )
        );
    }

    public DiversityTrimming5Distribution getDiversityTrimming5Distribution(String blockName) {
        return DiversityTrimming5Distribution.fromMap(
                ConverterUtils.convertSegmentTrimming(
                        getProbabilities1(blockName),
                        segmentLibrary,
                        DiversitySegment.class
                )
        );
    }

    public DiversityTrimming3Distribution getDiversityTrimming3Distribution(String blockName) {
        return DiversityTrimming3Distribution.fromMap(
                ConverterUtils.convertTwoSideTrimming(
                        getProbabilities2(blockName),
                        segmentLibrary,
                        DiversitySegment.class
                )
        );
    }

    public NucleotideDistribution getNucleotideDistribution(String blockName) {
        return NucleotideDistribution.fromMap(
                ConverterUtils.convertNucleotide(
                        getProbabilities(blockName)
                )
        );
    }

    public NucleotidePairDistribution getNucleotidePairDistribution(String blockName) {
        return NucleotidePairDistribution.fromMap(
                ConverterUtils.convertNucleotidePair(
                        getProbabilities1(blockName)
                )
        );
    }
}
