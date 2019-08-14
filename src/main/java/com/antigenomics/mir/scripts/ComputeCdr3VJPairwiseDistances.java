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

@CommandLine.Command(name = "cdr3aavj-pairwise-dist",
        sortOptions = false,
        description = "Returns a list of clonotype edges for a RepSeq sample. " +
                "All pairs of clonotypes from two samples are analyzed. " +
                "Scores for CDR3, V and J amino acid sequence alignments are returned," +
                "sequential IDs of each clonotype in a given sample are used as keys in" +
                "the output table.",
        mixinStandardHelpOptions = true)
public class ComputeCdr3VJPairwiseDistances extends ClonotypeTableBaseScript {
    @CommandLine.Option(names = {"-m1", "--mat-cdr3"},
            paramLabel = "<String>",
            defaultValue = "BLOSUM62",
            description = "Name of the alignment matrix for CDR3 region alignment (default: ${DEFAULT-VALUE})")
    private String m1;

    @CommandLine.Option(names = {"-m2", "--mat-vj"},
            paramLabel = "<String>",
            defaultValue = "BLOSUM62",
            description = "Name of the alignment matrix for V/J segment alignment (default: ${DEFAULT-VALUE})")
    private String m2;

    private final AtomicLong warningCounter = new AtomicLong(0);

    @Override
    @SuppressWarnings("unchecked")
    public Void call() throws Exception {
        var pipes = getClonotypePipes().toArray();
        if (pipes.length != 2) {
            throw new RuntimeException("Exactly two samples should be provided in input");
        }
        var firstSample = (ClonotypeTablePipe<Clonotype>) pipes[0];
        var secondSample = (ClonotypeTablePipe<Clonotype>) pipes[1];
        AlignmentScoring<AminoAcidSequence>
                cdr3matrix = AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.valueOf(m1)),
                vjmatrix = AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.valueOf(m2));
        var distances = new Cdr3VJPairwiseDistances<>(firstSample, secondSample, cdr3matrix, vjmatrix);

        var writer = new PrintWriter(getOutputStream("dist.txt"));

        writer.println("id1\tid2\tv.score\tj.score\tcdr3.score");
        distances.parallelStream().
                forEach(x ->
                {
                    ClonotypeCall<Clonotype> from = x.getFrom(), to = x.getTo();
                    writer.println(from.getId() + "\t" + to.getId() + "\t" +
                            getScoreSafe(x.getvAlignment(), "V", from, to) + "\t" +
                            getScoreSafe(x.getjAlignment(), "J", from, to) + "\t" +
                            getScoreSafe(x.getCdr3Alignment(), "CDR3", from, to));
                });

        writer.close();

        return null;
    }

    private float getScoreSafe(Alignment a, String alignmentType, ClonotypeCall from, ClonotypeCall to) {
        if (a != null) {
            return a.getScore();
        } else {
            if (warningCounter.incrementAndGet() <= 10) {
                System.out.println("[WARNING (showing first 10 warnings)] Failed to get alignment score for '" +
                        alignmentType + "' alignment between\n" + from + "\nand\n" + to);
            }
            return Float.NaN;
        }
    }
}
