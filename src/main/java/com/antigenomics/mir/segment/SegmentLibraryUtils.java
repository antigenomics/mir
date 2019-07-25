package com.antigenomics.mir.segment;

import com.antigenomics.mir.Species;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class SegmentLibraryUtils {
    private SegmentLibraryUtils() {

    }

    public static SegmentLibraryBundle<SegmentLibraryImpl> getBuiltinTcrAbLibraryBundle() {
        Map<SpeciesGeneTuple, SegmentLibraryImpl> segmentLibraryMap = new HashMap<>();

        for (Species species : Arrays.asList(Species.Human, Species.Mouse, Species.Monkey)) {
            for (Gene gene : Arrays.asList(Gene.TRA, Gene.TRB)) {
                segmentLibraryMap.put(new SpeciesGeneTuple(species, gene),
                        MigecSegmentLibraryUtils.getLibraryFromResources(species, gene));
            }
        }

        return new SegmentLibraryBundle<>(segmentLibraryMap);
    }
}
