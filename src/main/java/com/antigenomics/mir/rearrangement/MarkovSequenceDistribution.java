package com.antigenomics.mir.rearrangement;

import com.milaboratory.core.sequence.Alphabet;
import com.milaboratory.core.sequence.Sequence;
import com.antigenomics.mir.probability.ByteDistribution;
import com.antigenomics.mir.probability.ConditionalDistribution1;

public class MarkovSequenceDistribution<S extends Sequence<S>, D0 extends ByteDistribution> {
    private final D0 marginal;
    private final ConditionalDistribution1<Byte, Byte, D0> conditional;
    private final Alphabet<S> alphabet;
    private final boolean reverse;

    public MarkovSequenceDistribution(D0 marginal, ConditionalDistribution1<Byte, Byte, D0> conditional,
                                      boolean reverse,
                                      Alphabet<S> alphabet) {
        this.alphabet = alphabet;
        this.marginal = marginal;
        this.conditional = conditional;
        this.reverse = reverse;
    }

    public S generate(int length) {
        if (reverse) {
            return generateReverse(length);
        } else {
            return generateForward(length);
        }
    }

    S generateForward(int length) {
        if (length > 0) {
            byte[] arr = new byte[length];

            var prev = marginal.getDistributionSampler().generate();
            arr[0] = prev;

            for (int i = 1; i < length; i++) {
                prev = conditional.getDistribution0(prev).getDistributionSampler().generate();
                arr[i] = prev;
            }
            return alphabet.createBuilder().append(arr).createAndDestroy();
        } else {
            return alphabet.getEmptySequence();
        }
    }

    S generateReverse(int length) {
        if (length > 0) {
            byte[] arr = new byte[length];

            var prev = marginal.getDistributionSampler().generate();
            arr[length - 1] = prev;

            for (int i = length - 2; i >= 0; i--) {
                prev = conditional.getDistribution0(prev).getDistributionSampler().generate();
                arr[i] = prev;
            }
            return alphabet.createBuilder().append(arr).createAndDestroy();
        } else {
            return alphabet.getEmptySequence();
        }
    }

    public D0 getMarginal() {
        return marginal;
    }

    public ConditionalDistribution1<Byte, Byte, D0> getConditional() {
        return conditional;
    }

    public Alphabet<S> getAlphabet() {
        return alphabet;
    }

    public boolean isReverse() {
        return reverse;
    }
}
