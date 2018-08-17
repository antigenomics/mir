package com.milaboratory.mir.model.parser;

import java.io.InputStream;

final class MuruganModelStream {
    private final InputStream params, marginals;

    MuruganModelStream(InputStream params, InputStream marginals) {
        this.params = params;
        this.marginals = marginals;
    }

    InputStream getParams() {
        return params;
    }

    InputStream getMarginals() {
        return marginals;
    }
}
