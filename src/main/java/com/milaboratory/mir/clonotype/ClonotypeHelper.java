package com.milaboratory.mir.clonotype;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.rearrangement.ReadlessClonotypeImpl;
import com.milaboratory.mir.segment.*;

import java.util.ArrayList;

public final class ClonotypeHelper {
    // todo: segment library for helper & cache
    private static final SegmentLibrary segmentLibrary = new MockSegmentLibrary(Species.Human,
            Gene.TRB);

    public static Clonotype clonotypeFrom(String cdr3nt) {
        return new ReadlessClonotypeImpl(new NucleotideSequence(cdr3nt));
    }

    public static Clonotype clonotypeFrom(String cdr3nt, String v, String j) {
        return new ReadlessClonotypeImpl(new NucleotideSequence(cdr3nt),
                segmentLibrary.getV(v), MissingDiversitySegment.INSTANCE,
                segmentLibrary.getJ(j), MissingConstantSegment.INSTANCE);
    }

    @SuppressWarnings("unchecked")
    public static ClonotypeTable clonotypeTableFrom(Clonotype... clonotype) {
        var clonotypeList = new ArrayList<ClonotypeCall>();
        for (int i = 0; i < clonotype.length; i++) {
            var c = clonotype[i];
            if (c instanceof ClonotypeCall) {
                clonotypeList.add((ClonotypeCall) c);
            } else {
                clonotypeList.add(new ClonotypeCall(i, 1, 1.0 / clonotype.length, c));
            }
        }
        return new ClonotypeTable(clonotypeList);
    }
}
