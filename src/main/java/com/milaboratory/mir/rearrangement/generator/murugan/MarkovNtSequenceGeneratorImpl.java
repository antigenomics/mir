package com.milaboratory.mir.rearrangement.generator.murugan;

import com.milaboratory.core.sequence.Alphabet;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.rearrangement.generator.MarkovSequenceGenerator;
import com.milaboratory.mir.probability.parser.HierarchicalModelFormula;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MarkovNtSequenceGeneratorImpl extends MarkovSequenceGenerator<NucleotideSequence> {
    public MarkovNtSequenceGeneratorImpl(Map<String, Double> probabilityMap) {
        this(probabilityMap, ThreadLocalRandom.current());
    }

    public MarkovNtSequenceGeneratorImpl(Map<String, Double> probabilityMap, Random random) {
        super(NucleotideSequence.ALPHABET,
                getJointProbability(probabilityMap, NucleotideSequence.ALPHABET),
                random);
    }

    static double[][] getJointProbability(Map<String, Double> probabilityMap,
                                          Alphabet<NucleotideSequence> alphabet) {
        double[][] jointProbability = new double[alphabet.size()][alphabet.size()];

        // un-flatten, base2 -> base1 -> probability
        var embeddedProbabilities = HierarchicalModelFormula.embed1Joint(probabilityMap);

        embeddedProbabilities.forEach((key2, kvp) -> {
            kvp.forEach((key1, value) -> {
                byte base1 = alphabet.symbolToCode(key1.charAt(0)),
                        base2 = alphabet.symbolToCode(key2.charAt(0));
                jointProbability[base1][base2] = value;
            });
        });

        return jointProbability;
    }
}
