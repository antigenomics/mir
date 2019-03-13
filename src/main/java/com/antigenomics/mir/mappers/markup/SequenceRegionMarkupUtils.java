package com.antigenomics.mir.mappers.markup;

import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.core.sequence.Sequence;

import java.util.EnumMap;

public final class SequenceRegionMarkupUtils {
    private SequenceRegionMarkupUtils() {

    }

    public static <S extends Sequence<S>> int targetToQueryPosition(int pos,
                                                                    Alignment<S> alignment) {
        if (pos <= alignment.getSequence1Range().getFrom()) {
            return alignment.getSequence2Range().getFrom();
        } else if (pos >= alignment.getSequence1Range().getTo()) {
            return alignment.getSequence2Range().getTo();
        } else {
            int newPos = alignment.convertToSeq2Position(pos);
            return newPos < 0 ? -newPos : newPos;
        }
    }

    public static <E extends Enum<E>> PrecomputedSequenceRegionMarkup<AminoAcidSequence, E>
    translateWithAnchor(
            PrecomputedSequenceRegionMarkup<NucleotideSequence, E> regionMarkup, int anchor
    ) {
        int offset = anchor % 3;
        var sequence = regionMarkup.getFullSequence();
        int size = ((sequence.size() - offset) / 3) * 3; // also trim incomplete codon at the end
        var trimmedSequence = sequence.getRange(offset, offset + size);
        var regions = regionMarkup.getAllRegions().values();
        var regionMap = new EnumMap<E, SequenceRegion<AminoAcidSequence, E>>(regionMarkup.regionTypeClass);
        for (SequenceRegion<NucleotideSequence, E> region : regions) {
            int start = region.getStart() - offset;
            if (start < 0) {
                start = 0;
            }
            int end = region.getEnd() - offset;
            if (end < 0) {
                end = 0;
            } else if (end > size) {
                end = size;
            }
            start /= 3;
            end /= 3;

            regionMap.put(region.getRegionType(),
                    start >= end ?
                            SequenceRegion.empty(region.getRegionType(),
                                    AminoAcidSequence.ALPHABET,
                                    start) :
                            new SequenceRegion<>(region.getRegionType(),
                                    AminoAcidSequence.translateFromLeft(trimmedSequence.getRange(start * 3, end * 3)),
                                    start, end));
        }
        return new PrecomputedSequenceRegionMarkup<>(
                AminoAcidSequence.translateFromLeft(trimmedSequence),
                regionMap,
                regionMarkup.getRegionTypeClass()
        );
    }

    public static <E extends Enum<E>> ArrayBasedSequenceRegionMarkup<AminoAcidSequence, E>
    translateWithAnchor(
            ArrayBasedSequenceRegionMarkup<NucleotideSequence, E> regionMarkup, int anchor
    ) {
        // todo: better impl
        return translateWithAnchor(regionMarkup.asPrecomputed(), anchor).asArrayBased();
    }
}
