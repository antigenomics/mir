package com.milaboratory.mir.mappers.markup;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.milaboratory.core.sequence.GeneticCode;
import com.milaboratory.core.sequence.NucleotideSequence;

import java.util.EnumMap;

public final class SequenceRegionMarkupUtils {
    private SequenceRegionMarkupUtils() {

    }

    public static <E extends Enum<E>> SequenceRegion<AminoAcidSequence, E> translate(
            SequenceRegion<NucleotideSequence, E> region, boolean trimFivePrime) {
        int offset = region.getSize() % 3;
        int start = region.getStart(), end = region.getEnd();
        if (trimFivePrime) {
            start += offset;
        } else {
            end -= offset;
        }

        if (start >= end) {
            return SequenceRegion.empty(region.getRegionType(), AminoAcidSequence.ALPHABET);
        }

        // a bit dirty here, don't want to copy too much
        byte[] dest = new byte[region.getSize() / 3];
        GeneticCode.translate(dest, 0,
                region.getSequence(),
                start, end - start);

        return new SequenceRegion<>(region.getRegionType(),
                new AminoAcidSequence(dest),
                start, end, region.isIncomplete());
    }

    public static <E extends Enum<E>> PrecomputedSequenceRegionMarkup<AminoAcidSequence, E> translate(
            PrecomputedSequenceRegionMarkup<NucleotideSequence, E> regionMarkup, boolean trimFivePrime
    ) {
        var regionMap = new EnumMap<E, SequenceRegion<AminoAcidSequence, E>>(regionMarkup.regionTypeClass);
        for (SequenceRegion<NucleotideSequence, E> region : regionMarkup.getAllRegions().values()) {
            regionMap.put(region.getRegionType(), translate(region, trimFivePrime));
        }

        int len = regionMarkup.getFullSequence().size();
        int offset = len % 3;
        int start = 0, end = len;
        if (trimFivePrime) {
            start += offset;
        } else {
            end -= offset;
        }
        byte[] dest = new byte[len / 3];
        GeneticCode.translate(dest, 0,
                regionMarkup.getFullSequence().getSequence(),
                start, end - start);

        return new PrecomputedSequenceRegionMarkup<>(
                new AminoAcidSequence(dest),
                regionMap,
                regionMarkup.getRegionTypeClass()
        );
    }

}
