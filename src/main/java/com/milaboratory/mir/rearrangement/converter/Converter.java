package com.milaboratory.mir.rearrangement.converter;

import com.milaboratory.mir.probability.parser.HierarchicalModelFormula;
import com.milaboratory.mir.probability.parser.PlainTextHierarchicalModel;
import com.milaboratory.mir.probability.parser.PlainTextHierarchicalModelUtils;
import com.milaboratory.mir.rearrangement.RearrangementModel;
import com.milaboratory.mir.rearrangement.blocks.*;
import com.milaboratory.mir.segment.*;

import java.util.Map;

public abstract class Converter<T extends PlainTextHierarchicalModel, V extends RearrangementModel> {
    protected final T plainTextHierarchicalModel;
    protected final SegmentLibrary segmentLibrary;

    public Converter(T plainTextHierarchicalModel, SegmentLibrary segmentLibrary) {
        this.plainTextHierarchicalModel = plainTextHierarchicalModel;
        this.segmentLibrary = segmentLibrary;
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

    protected VariableDistribution getVariableDistribution(String blockName) {
        return VariableDistribution.fromMap(
                ConverterUtils.convertSegment(
                        getProbabilities(blockName),
                        segmentLibrary,
                        VariableSegment.class
                )
        );
    }

    protected DiversityDistribution getDiversityDistribution(String blockName) {
        return DiversityDistribution.fromMap(
                ConverterUtils.convertSegment(
                        getProbabilities(blockName),
                        segmentLibrary,
                        DiversitySegment.class
                )
        );
    }

    protected JoiningDistribution getJoiningDistribution(String blockName) {
        return JoiningDistribution.fromMap(
                ConverterUtils.convertSegment(
                        getProbabilities(blockName),
                        segmentLibrary,
                        JoiningSegment.class
                )
        );
    }

    protected DiversityJoiningDistribution getDiversityJoiningDistribution(String blockName) {
        return DiversityJoiningDistribution.fromMap(
                ConverterUtils.convertSegment(
                        getProbabilities1(blockName),
                        segmentLibrary,
                        DiversitySegment.class,
                        JoiningSegment.class
                )
        );
    }

    protected JoiningVariableDistribution getJoiningVariableDistribution(String jVariableName,
                                                                         String vVariableName) {
        HierarchicalModelFormula formula = plainTextHierarchicalModel.getFormula();
        var parents = formula.getParentVariables(jVariableName);

        if (parents.contains(vVariableName) && parents.size() == 1) {
            // return as is

            // todo: move to utils, check other cases when order can be different
            return getJoiningVariableDistribution(jVariableName +
                    PlainTextHierarchicalModelUtils.CONDITIONAL_SEPARATOR + vVariableName);
        } else if (parents.isEmpty()) {
            // no parents - just mock conditional by adding all V values
            return getJoiningVariableDistribution(
                    PlainTextHierarchicalModelUtils.mockConditional1(
                            plainTextHierarchicalModel.listValues(vVariableName),
                            getProbabilities(jVariableName))
            );
        }

        // Neither P(J|V) nor P(J) - something is messed up
        throw new RuntimeException("Bad J formula: '" + formula.getBlockName(jVariableName) + "'");
    }

    protected JoiningVariableDistribution getJoiningVariableDistribution(String blockName) {
        return getJoiningVariableDistribution(getProbabilities1(blockName));
    }

    protected JoiningVariableDistribution getJoiningVariableDistribution(Map<String, Map<String, Double>> probabilityMap) {
        return JoiningVariableDistribution.fromMap(
                ConverterUtils.convertSegment(
                        probabilityMap,
                        segmentLibrary,
                        JoiningSegment.class,
                        VariableSegment.class
                )
        );
    }

    protected DiversityJoiningVariableDistribution getDiversityJoiningVariableDistribution(String dVariableName,
                                                                                           String jVariableName,
                                                                                           String vVariableName) {
        HierarchicalModelFormula formula = plainTextHierarchicalModel.getFormula();
        var parents = formula.getParentVariables(dVariableName);

        if (parents.contains(vVariableName) && parents.contains(jVariableName) && parents.size() == 2) {
            // return as is
            return getDiversityJoiningVariableDistribution(
                    dVariableName + PlainTextHierarchicalModelUtils.CONDITIONAL_SEPARATOR +
                            jVariableName + PlainTextHierarchicalModelUtils.VARIABLE_SEPARATOR + vVariableName
            );
        } else if (parents.contains(jVariableName) && parents.size() == 1) {
            // J as parent - mock conditional by adding all V values
            return getDiversityJoiningVariableDistribution(
                    PlainTextHierarchicalModelUtils.mockConditional2(
                            plainTextHierarchicalModel.listValues(vVariableName),
                            getProbabilities1(
                                    dVariableName + PlainTextHierarchicalModelUtils.CONDITIONAL_SEPARATOR +
                                            jVariableName
                            )
                    )
            );
        } else if (parents.isEmpty()) {
            // no parents - mock conditional by first adding all J values and then all V values
            return getDiversityJoiningVariableDistribution(
                    PlainTextHierarchicalModelUtils.mockConditional2(
                            plainTextHierarchicalModel.listValues(vVariableName),
                            PlainTextHierarchicalModelUtils.mockConditional1(
                                    plainTextHierarchicalModel.listValues(jVariableName),
                                    getProbabilities(dVariableName))
                    )
            );
        }

        // Neither P(J|V) nor P(J) - something is messed up
        throw new RuntimeException("Bad D formula: '" + formula.getBlockName(dVariableName) + "'");
    }

    protected DiversityJoiningVariableDistribution getDiversityJoiningVariableDistribution(String blockName) {
        return getDiversityJoiningVariableDistribution(
                getProbabilities2(blockName)
        );
    }

    protected DiversityJoiningVariableDistribution getDiversityJoiningVariableDistribution(
            Map<String, Map<String, Map<String, Double>>> probabilityMap
    ) {
        return DiversityJoiningVariableDistribution.fromMap(
                ConverterUtils.convertSegment(
                        probabilityMap,
                        segmentLibrary,
                        DiversitySegment.class,
                        JoiningSegment.class,
                        VariableSegment.class
                )
        );
    }

    protected InsertSizeDistribution getInsertSizeDistribution(String blockName) {
        return InsertSizeDistribution.fromMap(
                ConverterUtils.convertIndelSize(
                        getProbabilities(blockName)
                )
        );
    }

    protected TrimmingDistribution getTrimmingDistribution(String blockName) {
        return TrimmingDistribution.fromMap(
                ConverterUtils.convertIndelSize(
                        getProbabilities(blockName)
                )
        );
    }

    protected VariableTrimmingDistribution getVariableTrimmingDistribution(String blockName) {
        return VariableTrimmingDistribution.fromMap(
                ConverterUtils.convertSegmentTrimming(
                        getProbabilities1(blockName),
                        segmentLibrary,
                        VariableSegment.class
                )
        );
    }

    protected JoiningTrimmingDistribution getJoiningTrimmingDistribution(String blockName) {
        return JoiningTrimmingDistribution.fromMap(
                ConverterUtils.convertSegmentTrimming(
                        getProbabilities1(blockName),
                        segmentLibrary,
                        JoiningSegment.class
                )
        );
    }

    protected DiversityTrimming5Distribution getDiversityTrimming5Distribution(String blockName) {
        return DiversityTrimming5Distribution.fromMap(
                ConverterUtils.convertSegmentTrimming(
                        getProbabilities1(blockName),
                        segmentLibrary,
                        DiversitySegment.class
                )
        );
    }

    protected DiversityTrimming3Distribution getDiversityTrimming3Distribution(String blockName) {
        return DiversityTrimming3Distribution.fromMap(
                ConverterUtils.convertTwoSideTrimming(
                        getProbabilities2(blockName),
                        segmentLibrary,
                        DiversitySegment.class
                )
        );
    }

    protected NucleotideDistribution getNucleotideDistribution(String blockName) {
        return NucleotideDistribution.fromMap(
                ConverterUtils.convertNucleotide(
                        getProbabilities(blockName)
                )
        );
    }

    protected NucleotidePairDistribution getNucleotidePairDistribution(String blockName) {
        return NucleotidePairDistribution.fromMap(
                ConverterUtils.convertNucleotidePair(
                        getProbabilities1(blockName)
                )
        );
    }

    public abstract V getRearrangementModel();
}
