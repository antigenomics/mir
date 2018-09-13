package com.milaboratory.mir.mappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class HitList<H extends Hit> {
    private final List<H> hits;

    public HitList(List<H> hits) {
        this(hits, false);
    }

    protected HitList(List<H> hits, boolean unsafe) {
        this.hits = Collections.unmodifiableList(unsafe ? hits : new ArrayList<>(hits));
    }

    public Optional<H> getBestHit() {
        return hits
                .stream()
                .max(H::compareTo);
    }

    public List<H> getHits() {
        return hits;
    }
}
