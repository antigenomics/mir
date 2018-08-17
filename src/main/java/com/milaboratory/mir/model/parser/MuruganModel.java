package com.milaboratory.mir.model.parser;

import com.milaboratory.mir.Gene;
import com.milaboratory.mir.Species;
import com.milaboratory.mir.model.probability.PlainTextProbabilisticModel;
import com.milaboratory.mir.model.probability.ProbabilisticModelFormula;

import java.util.Map;

public class MuruganModel extends PlainTextProbabilisticModel {
    private final Species species;
    private final Gene gene;

    public MuruganModel(ProbabilisticModelFormula formula,
                        Map<String, Map<String, Double>> probabilities,
                        Species species, Gene gene) {
        super(formula, probabilities);
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
