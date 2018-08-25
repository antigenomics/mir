package com.milaboratory.mir.rearrangement.parser;

import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.rearrangement.probability.PlainTextProbabilisticModel;
import com.milaboratory.mir.rearrangement.probability.ProbabilisticModelFormula;

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
