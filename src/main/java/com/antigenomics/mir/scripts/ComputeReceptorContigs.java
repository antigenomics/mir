package com.antigenomics.mir.scripts;

import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.clonotype.ClonotypeCall;
import com.antigenomics.mir.clonotype.io.ClonotypeTablePipe;
import com.antigenomics.mir.graph.Cdr3VJPairwiseDistances;
import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.Alignment;
import com.milaboratory.core.alignment.AlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import com.milaboratory.core.sequence.AminoAcidSequence;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicLong;

@CommandLine.Command(name = "compute-receptor-contigs",
        sortOptions = false,
        description = "Returns a list of full TCR/BCR nucleotide sequences for a sample.",
        mixinStandardHelpOptions = true)
public class ComputeReceptorContigs extends ClonotypeTableBaseScript {

    @Override
    @SuppressWarnings("unchecked")
    public Void call() throws Exception {
        var pipes = getClonotypePipes().toArray();
        if (pipes.length != 1) {
            throw new RuntimeException("Exactly one samples should be provided in input");
        }
        try (var writer = new PrintWriter(getOutputStream("contigs.txt"))) {
            writer.println("id\tcontig.nt");
            ((ClonotypeTablePipe<Clonotype>) pipes[0]).stream().forEach(x ->
                    writer.println(x.getId() + "\t" + x.getGermlineContigNt())
            );
        }

        return null;
    }
}
