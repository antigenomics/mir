package com.antigenomics.mir.rearrangement;

import com.milaboratory.core.sequence.AminoAcidSequence;
import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.Species;
import com.antigenomics.mir.segment.MigecSegmentLibraryUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RearrangementModelTest {
    @Test
    public void speedBenchmark() {
        runTest(100000, Species.Human, Gene.TRA);
        runTest(100000, Species.Human, Gene.TRB);
        runTest(100000, Species.Human, Gene.IGH);
        runTest(100000, Species.Mouse, Gene.TRB);
    }

    @Test
    public void oofBenchmarkTRA() {
        int total = 100000;
        double ncFreq = countOff(runTest(total, Species.Human, Gene.TRB)) / (double) total;
        System.out.println(ncFreq);
    }

    static long countOff(List<Rearrangement> rearrangements) {
        return rearrangements
                .stream()
                .map(Rearrangement::getCdr3)
                .filter(x -> x.size() % 3 != 0 || AminoAcidSequence.translateFromCenter(x).containStops())
                .count();
    }

    static List<Rearrangement> runTest(int n, Species s, Gene g) {
        var rearrMdl = RearrangementModelUtils.loadMuruganModel(
                MigecSegmentLibraryUtils.getLibraryFromResources(s, g)
        );

        long start, end;

        var rearrangements = new ArrayList<Rearrangement>(n);
        start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            rearrangements.add(((RearrangementTemplate) rearrMdl.generate()).toRearrangement());
        }
        end = System.currentTimeMillis();

        float factor = 3600.0f * 1000.0f / (end - start);

        System.out.println("Generating ~" + factor * n + " " + s + " " + g +
                " rearrangements per hour");

        return rearrangements;
    }
}
