package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.core.sequence.Sequence;
import com.milaboratory.mir.SequenceUtils;

import java.util.EnumMap;

public final class SequenceRegionMarkupUtils {
    private SequenceRegionMarkupUtils() {

    }

    public static <S extends Sequence<S>, E extends Enum<E>> PrecomputedSequenceRegionMarkup<S, E> realign(
            PrecomputedSequenceRegionMarkup<S, E> queryRegionMarkup, S targetSequence, Alignment<S> alignment) {
        if (!alignment.getSequence1().equals(queryRegionMarkup.getFullSequence())) {
            throw new IllegalArgumentException("Sequence in region markup and alignment don't match");
        }
        var regionMap = new EnumMap<E, SequenceRegion<S, E>>(queryRegionMarkup.regionTypeClass);
        var regions = queryRegionMarkup.getAllRegions().values();

        for (SequenceRegion<S, E> region : regions) {
            regionMap.put(region.getRegionType(), realign(region, targetSequence, alignment));
        }

        return new PrecomputedSequenceRegionMarkup<>(
                targetSequence,
                regionMap,
                queryRegionMarkup.getRegionTypeClass()
        );
    }

    public static <S extends Sequence<S>, E extends Enum<E>> SequenceRegion<S, E> realign(
            SequenceRegion<S, E> queryRegion, S targetSequence, Alignment<S> alignment
    ) {
        var range = SequenceUtils.stickToBoundaries(
                queryRegion.getStart(),
                queryRegion.getEnd(),
                alignment.getSequence1Range());

        return new SequenceRegion<>(queryRegion.getRegionType(),
                range.isEmpty() ? targetSequence.getAlphabet().getEmptySequence() : targetSequence.getRange(range),
                alignment.convertToSeq2Range(range));
    }

    public static <E extends Enum<E>> SequenceRegion<AminoAcidSequence, E> translate(
            SequenceRegion<NucleotideSequence, E> region, boolean trimFivePrime,
            NucleotideSequence parent) {
        var result = SequenceUtils.translateWithTrimming(parent, trimFivePrime,
                region.getStart(), region.getEnd());

        return result.isEmpty() ?
                SequenceRegion.empty(region.getRegionType(), AminoAcidSequence.ALPHABET,
                        result.getStart()) :
                new SequenceRegion<>(region.getRegionType(), result.getSequence(),
                        result.getStart(), result.getEnd());
    }

    public static <E extends Enum<E>> PrecomputedSequenceRegionMarkup<AminoAcidSequence, E> translate(
            PrecomputedSequenceRegionMarkup<NucleotideSequence, E> regionMarkup, boolean trimFivePrime
    ) {
        var regionMap = new EnumMap<E, SequenceRegion<AminoAcidSequence, E>>(regionMarkup.regionTypeClass);
        var regions = regionMarkup.getAllRegions().values();

        for (SequenceRegion<NucleotideSequence, E> region : regions) {
            regionMap.put(region.getRegionType(), translate(region,
                    regions.size() == 1 ? trimFivePrime : region.getStart() == 0,
                    regionMarkup.fullSequence));
        }

        return new PrecomputedSequenceRegionMarkup<>(
                SequenceUtils.translateWithTrimming(regionMarkup.getFullSequence(), trimFivePrime).getSequence(),
                regionMap,
                regionMarkup.getRegionTypeClass()
        );
    }
}
