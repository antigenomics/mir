package com.milaboratory.mir.mappers;

import com.milaboratory.mir.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class HitList<H extends Hit> {
    private final List<H> hits;
    private final boolean sorted;

    public HitList(List<H> hits) {
        this(hits, false, false);
    }

    protected HitList(List<H> hits, boolean unsafe, boolean sorted) {
        this.hits = Collections.unmodifiableList(unsafe ? hits : new ArrayList<>(hits));
        this.sorted = sorted;
    }

    public Optional<H> getBestHit() {
        return hits
                .stream()
                .max(H::compareTo);
    }

    public List<H> getKBestHits(int k, boolean fair) {
        return CollectionUtils.getKFirst(hits, k, !sorted, fair);
    }

    public List<H> getHits() {
        return hits;
    }
}
