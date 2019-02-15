package com.milaboratory.mir.graph;

import com.milaboratory.mir.TestUtils;
import com.milaboratory.mir.clonotype.ClonotypeCall;
import com.milaboratory.mir.clonotype.ClonotypeHelper;
import com.milaboratory.mir.clonotype.ClonotypeTable;
import com.milaboratory.mir.clonotype.io.ClonotypeTablePipe;
import com.milaboratory.mir.clonotype.parser.ClonotypeTableParserUtils;
import com.milaboratory.mir.clonotype.parser.MixcrClonotypeParserFactory;
import com.milaboratory.mir.clonotype.parser.Software;
import com.milaboratory.mir.clonotype.rearrangement.ReadlessClonotypeImpl;
import com.milaboratory.mir.pipe.ReproducibleGeneratorPipe;
import com.milaboratory.mir.rearrangement.RearrangementTemplate;
import com.milaboratory.mir.rearrangement.converter.MuruganConverterUtils;
import com.milaboratory.mir.rearrangement.parser.MuruganModelParserUtils;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.segment.parser.MigecSegmentLibraryUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class Cdr3NtScopedGraphTest {
    @Test
    public void test1() {
        var mdl = MuruganConverterUtils.asRearrangementModel(
                MuruganModelParserUtils.getModelFromResources(Species.Human, Gene.TRB),
                MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRB)
        );

        var input = new ReproducibleGeneratorPipe<>(
                () -> ((RearrangementTemplate) mdl.generate()).toRearrangement(),
                10000);

        new Cdr3NtScopedGraph<>(input,
                new Scope2DParameters(1, 6, 3),
                true)
                .parallelStream()
                .limit(20)
                .forEach(System.out::println);
    }

    @Test
    public void speedTest() {
        //var sampleSupplier = TestUtils.streamSupplierFrom("samples/mixcr_1.txt.gz");
        //var library = MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.IGH);
        //var parserFactory = new MixcrClonotypeParserFactory(library, true);
        //var io = new ClonotypeTablePipe<>(sampleSupplier.get(), parserFactory);

        var mdl = MuruganConverterUtils.asRearrangementModel(
                MuruganModelParserUtils.getModelFromResources(Species.Human, Gene.TRB),
                MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRB)
        );
        var io = new ReproducibleGeneratorPipe<>(
                () -> ((RearrangementTemplate) mdl.generate()).toRearrangement(),
                100000);

        var input = new ClonotypeTable(io, false);
        var params = new Scope2DParameters(3, 6, 3);

        long start, end;

        start = System.currentTimeMillis();
        new Cdr3NtScopedGraph<>(input, params, true)
                .parallelStream()
                .limit(20)
                .collect(Collectors.toList());
        end = System.currentTimeMillis();
        System.out.println("Time with no VJ grouping " + (end - start) + "ms");

        start = System.currentTimeMillis();
        new VJGroupedCdr3NtScopedGraph<>(input, params, false)
                .parallelStream()
                .limit(20)
                .collect(Collectors.toList());
        end = System.currentTimeMillis();
        System.out.println("Time with simple VJ grouping " + (end - start) + "ms");

        start = System.currentTimeMillis();
        new VJGroupedCdr3NtScopedGraph<>(input, params, true)
                .parallelStream()
                .limit(20)
                .collect(Collectors.toList());
        end = System.currentTimeMillis();
        System.out.println("Time with fuzzy VJ grouping " + (end - start) + "ms");
    }

    @Test
    public void test2() {
        var input = ClonotypeTableParserUtils.streamFrom(
                TestUtils.streamFrom("samples/mixcr_1.txt.gz"),
                Software.MIXCR, Species.Human, Gene.IGH);

        new Cdr3NtScopedGraph<>(input, new Scope2DParameters(1, 6, 3),
                true)
                .parallelStream()
                .limit(20)
                .forEach(System.out::println);
    }

    @Test
    public void neighborTest() {
        var input = ClonotypeHelper.clonotypeTableFrom(
                ClonotypeHelper.clonotypeFrom("TGTGCGACCCCGACCGGTTACTATTACTATTATGGAATGGACGTCTGG"),
                ClonotypeHelper.clonotypeFrom("TGTGCGACCCCGACCGGTTACTATTACTATTATGGAATGGACGTCTGG"),
                ClonotypeHelper.clonotypeFrom("TGTGCGACCCCGACCGGTTACTATTACTATTATGGAATGGACGTCTGG"),
                ClonotypeHelper.clonotypeFrom("TGTGCGACCCCGACCGGTTACTATTACTATTATGGAATGGACGTCTGG"),
                ClonotypeHelper.clonotypeFrom("TGTGCGACCCCGACCGGTTACTCTTACTATTATGGAATGGACGTCTGG")
        );

        //System.out.println(input);

        var res1 = new ArrayList<ClonotypeEdgeWithCdr3Alignment>();

        new Cdr3NtScopedGraph<>(input, new Scope2DParameters(0, 6, 3), false)
                .stream()
                .forEach(c -> res1.add((ClonotypeEdgeWithCdr3Alignment) c));

        for (ClonotypeEdgeWithCdr3Alignment c : res1) {
            var cFrom = (ClonotypeCall) c.getFrom();
            var cTo = (ClonotypeCall) c.getTo();
            System.out.println(cFrom.getId() + "\t" + cTo.getId());
        }
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