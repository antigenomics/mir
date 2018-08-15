package com.milaboratory.mir.model.parser;

import java.io.InputStream;

public class MuruganModelStream {
    private final InputStream params, marginals;

    public MuruganModelStream(InputStream params, InputStream marginals) {
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
