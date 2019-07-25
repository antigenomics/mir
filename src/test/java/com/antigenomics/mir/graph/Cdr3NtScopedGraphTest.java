package com.antigenomics.mir.graph;

import com.antigenomics.mir.TestUtils;
import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.clonotype.ClonotypeHelper;
import com.antigenomics.mir.clonotype.table.ClonotypeTable;
import com.antigenomics.mir.clonotype.io.ClonotypeTablePipe;
import com.antigenomics.mir.clonotype.parser.MixcrClonotypeParserFactory;
import com.antigenomics.mir.pipe.Pipe;
import com.antigenomics.mir.pipe.ReproducibleGeneratorPipe;
import com.antigenomics.mir.rearrangement.RearrangementTemplate;
import com.antigenomics.mir.rearrangement.converter.MuruganConverterUtils;
import com.antigenomics.mir.rearrangement.parser.MuruganModelParserUtils;
import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.Species;
import com.antigenomics.mir.segment.MigecSegmentLibraryUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Collectors;

public class Cdr3NtScopedGraphTest {
    private Scope2DParameters defaultParams = new Scope2DParameters(3, 6, 3);

    @Test
    public void speedTest1() {
        var mdl = MuruganConverterUtils.asRearrangementModel(
                MuruganModelParserUtils.getModelFromResources(Species.Human, Gene.TRB),
                MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRB)
        );
        var io = new ReproducibleGeneratorPipe<>(
                () -> ((RearrangementTemplate) mdl.generate()).toRearrangement(),
                10000);

        var input = new ClonotypeTable<>(io);

        runSpeedBenchmark(input, defaultParams, 100);
    }

    @Test
    public void speedTest2() {
        var sampleSupplier = TestUtils.streamSupplierFrom("samples/mixcr_1.txt.gz");
        var library = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.IGH);
        var parserFactory = new MixcrClonotypeParserFactory(library, true);
        var io = new ClonotypeTablePipe<>(sampleSupplier.get(), parserFactory);
        var input = new ClonotypeTable<>(io);
        var params = new Scope2DParameters(3, 6, 3);

        runSpeedBenchmark(input, params, 100);
    }

    private void runSpeedBenchmark(Pipe<? extends Clonotype> input,
                                   Scope2DParameters params, int nEdges) {
        long start, end;

        start = System.currentTimeMillis();
        new Cdr3NtScopedGraph<>(input, params, true)
                .parallelStream()
                .limit(nEdges)
                .collect(Collectors.toList());
        end = System.currentTimeMillis();
        System.out.println("Time with no VJ grouping " + (end - start) + "ms");

        start = System.currentTimeMillis();
        new VJGroupedCdr3NtScopedGraph<>(input, params, false)
                .parallelStream()
                .limit(nEdges)
                .collect(Collectors.toList());
        end = System.currentTimeMillis();
        System.out.println("Time with simple VJ grouping " + (end - start) + "ms");

        start = System.currentTimeMillis();
        new VJGroupedCdr3NtScopedGraph<>(input, params, true)
                .parallelStream()
                .limit(nEdges)
                .collect(Collectors.toList());
        end = System.currentTimeMillis();
        System.out.println("Time with fuzzy VJ grouping " + (end - start) + "ms");
    }

    //private void

    @Test
    public void neighborTest() {
        var input = ClonotypeHelper.clonotypeTableFrom(
                ClonotypeHelper.clonotypeFrom("TGTGCGACCCCGACCGGTTACTATTACTATTATGGAATGGACGTCTGG"),
                ClonotypeHelper.clonotypeFrom("TGTGCGACCCCGACCGGTTACTATTACTATTATGGAATGGACGTCTGG"),
                ClonotypeHelper.clonotypeFrom("TGTGCGACCCCGACCGGTTACTATTACTATTATGGAATGGACGTCTGG"),
                ClonotypeHelper.clonotypeFrom("TGTGCGACCCCGACCGGTTACTATTACTATTATGGAATGGACGTCTGG"),
                //                                                          *
                ClonotypeHelper.clonotypeFrom("TGTGCGACCCCGACCGGTTACTCTTACTATTATGGAATGGACGTCTGG"),
                //                                                          *          *
                ClonotypeHelper.clonotypeFrom("TGTGCGACCCCGACCGGTTACTCTTACTATTATCGAATGGACGTCTGG")
        );

        var res1 = new Cdr3NtScopedGraph<>(input,
                new Scope2DParameters(1, 1, 0),
                false)
                .stream()
                .collect(Collectors.toList());

        Assert.assertEquals(4 * 3 + 1 * 5 + 1, res1.size());

        var res2 = new Cdr3NtScopedGraph<>(input,
                new Scope2DParameters(2, 1, 0),
                false)
                .stream()
                .collect(Collectors.toList());

        Assert.assertEquals(4 * (3 + 1) + 1 * 5 + 1, res2.size());
    }

//    @Test
//    public void test3() {
//        var seq1 = new NucleotideSequence("ACGAGAGCGGCTGCCTCCAGGTAACGATTAATCCCTGGTACTTCTCTCTCT");
//        var seq2 = new NucleotideSequence("ACGAGAGATCGTGCGCGGTGACTACAAATTTTCGGTACTTCGCTCTCT");
//        var res12 =  Aligner.alignGlobalAffine(AffineGapAlignmentScoring.getNucleotideBLASTScoring(),
//                seq1,
//                seq2);
//
//        var res21 =  Aligner.alignGlobalAffine(AffineGapAlignmentScoring.getNucleotideBLASTScoring(),
//                seq2,
//                seq1);
//
//        System.out.println(res12);
//        System.out.println(res21);
//    }
}