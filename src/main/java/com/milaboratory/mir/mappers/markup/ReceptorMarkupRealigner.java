package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.segment.JoiningSegment;
import com.milaboratory.mir.segment.VariableSegment;
import com.milaboratory.mir.structure.AntigenReceptorRegionType;

import java.util.Optional;

public class ReceptorMarkupRealigner<S extends Sequence<S>>
        implements MarkupRealigner<S, AntigenReceptorRegionType, PrecomputedSequenceRegionMarkup<S, AntigenReceptorRegionType>> {
    private final SegmentMarkupRealigner<VariableSegment, S> variableSegmentRealigner;
    private final SegmentMarkupRealigner<JoiningSegment, S> joiningSegmentRealigner;

    public ReceptorMarkupRealigner(SegmentMarkupRealigner<VariableSegment, S> variableSegmentRealigner,
                                   SegmentMarkupRealigner<JoiningSegment, S> joiningSegmentRealigner) {
        this.variableSegmentRealigner = variableSegmentRealigner;
        this.joiningSegmentRealigner = joiningSegmentRealigner;
    }

    @Override
    public Optional<PrecomputedSequenceRegionMarkup<S, AntigenReceptorRegionType>> recomputeMarkup(S query) {
        var vMarkup = variableSegmentRealigner.recomputeMarkup(query);
        if (vMarkup.isPresent()) {
            var actualVMarkup = vMarkup.get();
            int vMarkupEnd = actualVMarkup.getEnd();

            if (vMarkupEnd == 0) {
                return Optional.empty();
            }

            if (vMarkupEnd < query.size()) {
                var jMarkup = joiningSegmentRealigner.recomputeMarkup(query.getRange(vMarkupEnd, query.size()));
                if (jMarkup.isPresent()) {
                    var actualJMarkup = jMarkup.get().padLeft(query.getRange(0, vMarkupEnd));
                    actualVMarkup = actualVMarkup.concatenate(actualJMarkup);
                }
            }

            return Optional.of(actualVMarkup);
        }
        return Optional.empty();
    }
}