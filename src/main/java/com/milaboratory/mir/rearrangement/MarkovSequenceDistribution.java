package com.milaboratory.mir.rearrangement;

import com.milaboratory.core.sequence.Alphabet;
import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.probability.ByteDistribution;
import com.milaboratory.mir.probability.ConditionalDistribution1;

public class MarkovSequenceDistribution<S extends Sequence<S>, D0 extends ByteDistribution> {
    private final D0 marginal;
    private final ConditionalDistribution1<Byte, Byte, D0> joint;
    private final Alphabet<S> alphabet;
    private final boolean reverse;

    public MarkovSequenceDistribution(D0 marginal, ConditionalDistribution1<Byte, Byte, D0> joint,
                                      boolean reverse,
                                      Alphabet<S> alphabet) {
        this.alphabet = alphabet;
        this.marginal = marginal;
        this.joint = joint;
        this.reverse = reverse;
    }

    public S generate(int length) {
        if (reverse) {
            return generateReverse(length);
        } else {
            return generateForward(length);
        }
    }

    public S generateForward(int length) {
        if (length > 0) {
            var builder = alphabet.createBuilder().ensureCapacity(length);
            var prev = marginal.getDistributionSampler().generate();
            builder.set(0, prev);

            for (int i = 1; i < length; i++) {
                prev = joint.getDistribution0(prev).getDistributionSampler().generate();
                builder.set(i, prev);
            }
            return builder.createAndDestroy();
        } else {
            return alphabet.getEmptySequence();
        }
    }

    public S generateReverse(int length) {
        if (length > 0) {
            var builder = alphabet.createBuilder().ensureCapacity(length);
            var prev = marginal.getDistributionSampler().generate();
            builder.set(length - 1, prev);

            for (int i = length - 2; i >= 0; i--) {
                prev = joint.getDistribution0(prev).getDistributionSampler().generate();
                builder.set(i, prev);
            }
            return builder.createAndDestroy();
        } else {
            return alphabet.getEmptySequence();
        }
    }
}
