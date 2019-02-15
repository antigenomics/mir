package com.milaboratory.mir.graph;

import com.milaboratory.mir.mappers.stm.SequenceSearchScope;

public class Scope2DParameters {
    private final int k;
    private final SequenceSearchScope ntSearchScope, aaSearchScope;

    public Scope2DParameters(int k, SequenceSearchScope ntSearchScope, SequenceSearchScope aaSearchScope) {
        this.k = k;
        this.ntSearchScope = ntSearchScope;
        this.aaSearchScope = aaSearchScope;
    }

    public Scope2DParameters(int k,
                             int maxSubstitutions, int maxIndels,
                             int maxAaSubstitutions, int maxAaIndels) {
        this(k, getSearchScope(maxSubstitutions, maxIndels),
                getSearchScope(
                        calcOrGetAaCount(maxSubstitutions, maxAaSubstitutions),
                        calcOrGetAaCount(maxIndels, maxAaIndels))
        );
    }

    public Scope2DParameters(int k,
                             int maxSubstitutions, int maxIndels) {
        this(k, maxSubstitutions, maxIndels, calcAaCount(maxSubstitutions), calcAaCount(maxIndels));
    }

    private static SequenceSearchScope getSearchScope(int maxSubstitutions, int maxIndels) {
        return new SequenceSearchScope(maxSubstitutions,
                maxIndels,
                maxSubstitutions + maxIndels,
                false,
                true);
    }

    private static int calcOrGetAaCount(int ntCount, int aaCount) {
        return aaCount < 0 ? calcAaCount(ntCount) : aaCount;
    }

    private static int calcAaCount(int ntCount) {
        return ntCount / 3 + (ntCount % 3 == 0 ? 0 : 1);
    }

    public int getK() {
        return k;
    }

    public SequenceSearchScope getNtSearchScope() {
        return ntSearchScope;
    }

    public SequenceSearchScope getAaSearchScope() {
        return aaSearchScope;
    }
}
