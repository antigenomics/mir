package com.milaboratory.mir.clonotype.structure;

import com.milaboratory.core.sequence.Sequence;

public class ReceptorRegion<S extends Sequence<S>> {
    private final ReceptorRegionType receptorRegionType;
    private final int startInParent, endInParent;
    private final S sequence;
    private final boolean isIncomplete;

    public ReceptorRegion(ReceptorRegionType receptorRegionType,
                          int startInParent, int endInParent, S sequence,
                          boolean isIncomplete) {
        this.receptorRegionType = receptorRegionType;
        this.startInParent = startInParent;
        this.endInParent = endInParent;
        this.sequence = sequence;
        this.isIncomplete = isIncomplete;
    }

    public ReceptorRegionType getReceptorRegionType() {
        return receptorRegionType;
    }

    public int getStartInParent() {
        return startInParent;
    }

    public int getEndInParent() {
        return endInParent;
    }

    public S getSequence() {
        return sequence;
    }

    public boolean isIncomplete() {
        return isIncomplete;
    }

    public boolean isEmpty() {
        return sequence.size() == 0;
    }
}
