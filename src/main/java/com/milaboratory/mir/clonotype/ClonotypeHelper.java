package com.milaboratory.mir.clonotype;

import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.clonotype.rearrangement.ReadlessClonotypeImpl;
import com.milaboratory.mir.segment.*;

import java.util.ArrayList;
import java.util.Map;

public final class ClonotypeHelper {
    // todo: segment library for helper & cache
    private static final SegmentLibrary segmentLibrary = new MockSegmentLibrary(Species.Human,
            Gene.TRB);

    public static Clonotype clonotypeFrom(String cdr3nt) {
        return new ReadlessClonotypeImpl(new NucleotideSequence(cdr3nt));
    }

    public static Clonotype clonotypeFromAa(String cdr3aa) {
        return clonotypeFrom(mockTranslate(cdr3aa));
    }

    public static Clonotype clonotypeFrom(String cdr3nt, String v, String j) {
        return new ReadlessClonotypeImpl(new NucleotideSequence(cdr3nt),
                segmentLibrary.getV(v), MissingDiversitySegment.INSTANCE,
                segmentLibrary.getJ(j), MissingConstantSegment.INSTANCE);
    }

    public static Clonotype clonotypeFromAa(String cdr3aa, String v, String j) {
        return clonotypeFrom(mockTranslate(cdr3aa), v, j);
    }

    // todo: from aa

    private static final Map<Character, String> mockCodons =
            Map.ofEntries(
                    Map.entry('A', "GCT"),
                    Map.entry('C', "TGT"),
                    Map.entry('D', "GAT"),
                    Map.entry('E', "GAA"),
                    Map.entry('F', "TTT"),
                    Map.entry('G', "GGT"),
                    Map.entry('I', "ATT"),
                    Map.entry('H', "CAT"),
                    Map.entry('K', "AAA"),
                    Map.entry('L', "TTA"),
                    Map.entry('M', "ATG"),
                    Map.entry('N', "AAT"),
                    Map.entry('P', "CCT"),
                    Map.entry('Q', "CAA"),
                    Map.entry('R', "CGT"),
                    Map.entry('S', "TCT"),
                    Map.entry('T', "ACT"),
                    Map.entry('V', "GTT"),
                    Map.entry('W', "TGG"),
                    Map.entry('Y', "TAT")
            );

    private static String mockTranslate(String cdr3aa) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < cdr3aa.length(); i++) {
            res.append(mockCodons.get(cdr3aa.charAt(i)));
        }
        return res.toString();
    }

    @SuppressWarnings("unchecked")
    public static ClonotypeTable<? extends Clonotype> clonotypeTableFrom(Clonotype... clonotype) {
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
