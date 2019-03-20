package com.antigenomics.mir.clonotype.table.filters.text;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.clonotype.ClonotypeCall;
import com.antigenomics.mir.segment.SegmentCall;

import java.util.List;
import java.util.stream.Collectors;

public class TextFilterVSegmentValueGetter<T extends Clonotype> implements TextFilterValueGetter<T> {

    @Override
    public String getValue(ClonotypeCall<T> clonotypeCall) {
        return clonotypeCall.getBestVariableSegment().toString();
    }

    @Override
    public boolean isSingleValue() {
        return false;
    }

    @Override
    public List<String> getValues(ClonotypeCall<T> clonotypeCall) {
        return clonotypeCall.getVariableSegmentCalls().stream().map(SegmentCall::toString).collect(Collectors.toList());
    }
}
