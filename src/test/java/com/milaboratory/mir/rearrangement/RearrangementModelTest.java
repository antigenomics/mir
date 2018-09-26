package com.milaboratory.mir.rearrangement;

import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.segment.parser.MigecSegmentLibraryUtils;
import org.junit.Test;

public class RearrangementModelTest {
    @Test
    public void speedBenchmarkTRA() {
        runTest(100000, Species.Human, Gene.TRA);
        runTest(100000, Species.Human, Gene.TRB);
        runTest(100000, Species.Human, Gene.IGH);
        runTest(100000, Species.Mouse, Gene.TRB);
    }

    static long runTest(int n, Species s, Gene g) {
        var rearrMdl = RearrangementModelUtils.loadMuruganModel(
                MigecSegmentLibraryUtils.getLibraryFromResources(s, g)
        );

        long start, end;

        start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            ((RearrangementTemplate) rearrMdl.generate()).toRearrangement().getCdr3();
        }
        end = System.currentTimeMillis();

        float factor = 3600.0f * 1000.0f / (end - start);

        System.out.println("Generating ~" + factor * n + " " + s + " " + g +
                " rearrangements per hour");

        return end - start;
    }
}
