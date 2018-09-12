package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.SequenceUtils;

import java.util.Collection;
import java.util.EnumMap;

public final class SequenceRegionMarkupUtils {
    private SequenceRegionMarkupUtils() {

    }

    public static <E extends Enum<E>> SequenceRegion<AminoAcidSequence, E> translate(
            SequenceRegion<NucleotideSequence, E> region, boolean trimFivePrime,
            NucleotideSequence parent) {
        var result = SequenceUtils.translateWithTrimming(parent, trimFivePrime,
                region.getStart(), region.getEnd());

        return result.isEmpty() ?
                SequenceRegion.empty(region.getRegionType(), AminoAcidSequence.ALPHABET) :
                new SequenceRegion<>(region.getRegionType(), result.getSequence(),
                        result.getStart(), result.getEnd(), region.isIncomplete());
    }

    public static <E extends Enum<E>> PrecomputedSequenceRegionMarkup<AminoAcidSequence, E> translate(
            PrecomputedSequenceRegionMarkup<NucleotideSequence, E> regionMarkup, boolean trimFivePrime
    ) {
        var regionMap = new EnumMap<E, SequenceRegion<AminoAcidSequence, E>>(regionMarkup.regionTypeClass);
        var regions = regionMarkup.getAllRegions().values();

        int i = regions.size() > 1 ? 0 : (trimFivePrime ? -1 : regions.size());
        for (SequenceRegion<NucleotideSequence, E> region : regions) {
            regionMap.put(region.getRegionType(), translate(region,
                    ++i < regions.size(),
                    regionMarkup.fullSequence));
        }

        return new PrecomputedSequenceRegionMarkup<>(
                SequenceUtils.translateWithTrimming(regionMarkup.getFullSequence(), trimFivePrime).getSequence(),
                regionMap,
                regionMarkup.getRegionTypeClass()
        );
    }
}
