package com.milaboratory.mir.rearrangement.generator;

import com.milaboratory.core.sequence.Alphabet;
import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.probability.ProbabilityMathUtils;

import java.util.Random;

public class MarkovSequenceGenerator<S extends Sequence<S>> implements SequenceGenerator<S> {
    private final double[] marginals;
    private final double[][] conditionals;
    private final Alphabet<S> alphabet;
    private final Random random;

    public MarkovSequenceGenerator(Alphabet<S> alphabet,
                                   double[][] jointProbability,
                                   Random random) {
        assert jointProbability.length == alphabet.size();
        assert jointProbability.length == jointProbability[0].length;

        this.alphabet = alphabet;
        this.marginals = ProbabilityMathUtils.toMarginal(jointProbability);
        assert Math.abs(ProbabilityMathUtils.sum(marginals) - 1.0d) < 1e-6;

        this.conditionals = ProbabilityMathUtils.toConditional(jointProbability, marginals);
        this.random = random;
    }

    public MarkovSequenceGenerator(Alphabet<S> alphabet,
                                   double[][] conditionals,
                                   double[] marginals,
                                   Random random) {
        this.alphabet = alphabet;
        this.conditionals = conditionals;
        this.marginals = marginals;
        this.random = random;
    }

    @Override
    public S generateForward(int length) {
        if (length > 0) {
            var builder = alphabet.createBuilder().ensureCapacity(length);
            byte prev = (byte) ProbabilityMathUtils.sample(marginals, random);
            builder.set(0, prev);

            for (int i = 1; i < length; i++) {
                prev = (byte) ProbabilityMathUtils.sample(conditionals[prev], random);
                builder.set(i, prev);
            }
            return builder.createAndDestroy();
        } else {
            return alphabet.getEmptySequence();
        }
    }

    @Override
    public S generateReverse(int length) {
        if (length > 0) {
            var builder = alphabet.createBuilder().ensureCapacity(length);
            byte prev = (byte) ProbabilityMathUtils.sample(marginals, random);
            builder.set(length - 1, prev);

            for (int i = length - 2; i >= 0; i--) {
                prev = (byte) ProbabilityMathUtils.sample(conditionals[prev], random);
                builder.set(i, prev);
            }
            return builder.createAndDestroy();
        } else {
            return alphabet.getEmptySequence();
        }
    }

    @Override
    public double getProbabilityForward(S seq) {
        return 0;
    }

    @Override
    public double getProbabilityReverse(S seq) {
        return 0;
    }
}
