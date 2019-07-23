package com.antigenomics.mir.clonotype;

import com.antigenomics.mir.segment.*;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface Clonotype {
    NucleotideSequence getCdr3Nt();

    AminoAcidSequence getCdr3Aa();

    List<SegmentCall<VariableSegment>> getVariableSegmentCalls();

    List<SegmentCall<DiversitySegment>> getDiversitySegmentCalls();

    List<SegmentCall<JoiningSegment>> getJoiningSegmentCalls();

    List<SegmentCall<ConstantSegment>> getConstantSegmentCalls();

    default VariableSegment getBestVariableSegment() {
        return getVariableSegmentCalls().stream().max(Comparator.naturalOrder())
                .map(SegmentCall::getSegment).orElseGet(() -> MissingVariableSegment.INSTANCE);
    }

    default DiversitySegment getBestDiversitySegment() {
        return getDiversitySegmentCalls().stream().max(Comparator.naturalOrder())
                .map(SegmentCall::getSegment).orElseGet(() -> MissingDiversitySegment.INSTANCE);
    }

    default JoiningSegment getBestJoiningSegment() {
        return getJoiningSegmentCalls().stream().max(Comparator.naturalOrder())
                .map(SegmentCall::getSegment).orElseGet(() -> MissingJoiningSegment.INSTANCE);
    }

    default ConstantSegment getBestConstantSegment() {
        return getConstantSegmentCalls().stream().max(Comparator.naturalOrder())
                .map(SegmentCall::getSegment).orElseGet(() -> MissingConstantSegment.INSTANCE);
    }

    default Map<String, String> getAnnotations() {
        return Collections.emptyMap();
    }

    default double getWeight() {
        return 1.0;
    }
}
