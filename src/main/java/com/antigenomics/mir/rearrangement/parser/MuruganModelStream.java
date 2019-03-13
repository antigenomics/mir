package com.antigenomics.mir.rearrangement.parser;

import java.io.InputStream;

public final class MuruganModelStream {
    private final InputStream params, marginals;

    MuruganModelStream(InputStream params, InputStream marginals) {
        this.params = params;
        this.marginals = marginals;
    }

    public InputStream getParams() {
        return params;
    }

    public InputStream getMarginals() {
        return marginals;
    }
}
