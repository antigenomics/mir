package com.antigenomics.mir.rearrangement.parser;

import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.Species;
import com.antigenomics.mir.probability.parser.PlainTextHierarchicalModel;
import com.antigenomics.mir.probability.parser.HierarchicalModelFormula;

import java.util.Map;

public class MuruganModel extends PlainTextHierarchicalModel {
    private final Species species;
    private final Gene gene;

    public MuruganModel(PlainTextHierarchicalModel plainTextHierarchicalModel,
                        Species species, Gene gene) {
        super(plainTextHierarchicalModel.getProbabilities());
        this.species = species;
        this.gene = gene;
    }

    public MuruganModel(Map<String, Map<String, Double>> probabilities,
                        HierarchicalModelFormula formula,
                        Species species, Gene gene) {
        super(probabilities, formula);
        this.species = species;
        this.gene = gene;
    }

    public Species getSpecies() {
        return species;
    }

    public Gene getGene() {
        return gene;
    }
}
