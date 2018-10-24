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
    public Optional<MarkupRealignmentResult<S, AntigenReceptorRegionType,
            PrecomputedSequenceRegionMarkup<S, AntigenReceptorRegionType>>> recomputeMarkup(S query) {
        var vResult = variableSegmentRealigner.recomputeMarkup(query);
        if (vResult.isPresent()) {
            var actualVResult = vResult.get();
            var vMarkup = actualVResult.getMarkup();
            int vMarkupEnd = vMarkup.getEnd();

            if (vMarkupEnd == 0) {
                return Optional.empty();
            }

            int vMatches = actualVResult.getNumberOfMatches(), jMatches = 0;
            double vCoverage = actualVResult.getCoverage(), jCoverage = 0;

            var variableSegment = (VariableSegment) actualVResult.getPayload();
            var joiningSegment = (JoiningSegment) null;

            if (vMarkupEnd < query.size()) {
                var jResult = joiningSegmentRealigner.recomputeMarkup(query.getRange(vMarkupEnd, query.size()));
                if (jResult.isPresent()) {
                    var actualJResult = jResult.get();
                    joiningSegment = (JoiningSegment) actualJResult.getPayload();
                    var jMarkup = actualJResult.getMarkup().padLeft(query.getRange(0, vMarkupEnd));
                    vMarkup = vMarkup.concatenate(jMarkup);

                    jMatches = actualJResult.getNumberOfMatches();
                    jCoverage = actualJResult.getCoverage();
                }
            }

            return Optional.of(
                    new MarkupRealignmentResult<>(
                            vMarkup,
                            vMatches + jMatches,
                            (vMatches * vCoverage + jMatches * jCoverage) / (vMatches + jMatches),
                            new VariableJoiningPair(
                                    variableSegment, joiningSegment
                            )
                    )
            );
        }
        return Optional.empty();
    }

    public static class VariableJoiningPair {
        private final VariableSegment variableSegment;
        private final JoiningSegment joiningSegment;

        public VariableJoiningPair(VariableSegment variableSegment, JoiningSegment joiningSegment) {
            this.variableSegment = variableSegment;
            this.joiningSegment = joiningSegment;
        }

        public VariableSegment getVariableSegment() {
            return variableSegment;
        }

        public JoiningSegment getJoiningSegment() {
            return joiningSegment;
        }

        @Override
        public String toString() {
            return "(" + variableSegment.getId() +
                    ", " + joiningSegment.getId() +
                    ")";
        }
    }
}