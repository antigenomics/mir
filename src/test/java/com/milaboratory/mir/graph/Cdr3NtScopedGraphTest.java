package com.milaboratory.mir.graph;

import com.milaboratory.mir.pipe.ReproducibleGeneratorPipe;
import com.milaboratory.mir.rearrangement.RearrangementTemplate;
import com.milaboratory.mir.rearrangement.converter.MuruganConverterUtils;
import com.milaboratory.mir.rearrangement.parser.MuruganModelParserUtils;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import com.milaboratory.mir.segment.parser.MigecSegmentLibraryUtils;
import org.junit.Test;

public class Cdr3NtScopedGraphTest {

    @Test
    public void test1() {
        var mdl = MuruganConverterUtils.asRearrangementModel(
                MuruganModelParserUtils.getModelFromResources(Species.Human, Gene.TRB),
                MigecSegmentLibraryUtils.getLibraryFromResources(Species.Human, Gene.TRB)
        );

        var input = new ReproducibleGeneratorPipe<>(() -> ((RearrangementTemplate) mdl.generate()).toRearrangement(),
                10000);

        new Cdr3NtScopedGraph<>(input, 3, 6, 3, 9)
                .stream()
                .forEach(System.out::println);
    }
}