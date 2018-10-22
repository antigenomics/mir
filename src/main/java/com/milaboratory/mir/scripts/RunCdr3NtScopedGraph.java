package com.milaboratory.mir.scripts;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.clonotype.parser.ClonotypeTableParserUtils;
import com.milaboratory.mir.clonotype.parser.Software;
import com.milaboratory.mir.graph.Cdr3NtScopedGraph;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "cdr3nt-graph",
        sortOptions = false,
        description = "Returns a list of clonotype edges for a RepSeq sample. " +
                "Will first search for CDR3 amino acid match and then perform CDR3 nucleotide alignment." +
                "Only edges with number of substitutions and indels within search scope (-s/-d) will be returned.",
        mixinStandardHelpOptions = true)
public class RunCdr3NtScopedGraph implements Callable<Void> {
    @CommandLine.Option(names = {"-I", "--input"},
            required = true,
            paramLabel = "<path[.gz]>",
            description = "Path to input file")
    private String inputPath;

    @CommandLine.Option(names = {"-O", "--output"},
            required = true,
            paramLabel = "<path[.gz]>",
            description = "Path to output file")
    private String outputPath;

    @CommandLine.Option(names = {"-F", "--software"},
            required = true,
            paramLabel = "<id>",
            description = "Software format of input file (allowed values: ${COMPLETION-CANDIDATES})")
    private Software software;

    @CommandLine.Option(names = {"-k", "--knn"},
            paramLabel = "<int>",
            defaultValue = "3",
            description = "Number of nearest neighbors to consider. If set to '-1' will list all neighbors (default: ${DEFAULT-VALUE})")
    private int kNn;

    @CommandLine.Option(names = {"-u", "--max-substs"},
            paramLabel = "<int>",
            defaultValue = "6",
            description = "Maximal number of NT substitutions allowed (default: ${DEFAULT-VALUE})")
    private int maxSubstitutions;

    @CommandLine.Option(names = {"-d", "--max-indels"},
            paramLabel = "<int>",
            defaultValue = "3",
            description = "Maximal number of NT indels allowed (default: ${DEFAULT-VALUE})")
    private int maxIndels;

    @CommandLine.Option(names = {"-S", "--species"},
            required = true,
            paramLabel = "<id>",
            description = "Specify organism (allowed values: ${COMPLETION-CANDIDATES})")
    private Species species;

    @CommandLine.Option(names = {"-G", "--gene"},
            required = true,
            paramLabel = "<id>",
            description = "Specify antigen receptor gene (allowed values: ${COMPLETION-CANDIDATES})")
    private Gene gene;

    @Override
    public Void call() throws Exception {
        var input = ClonotypeTableParserUtils.streamFrom(
                CommonUtils.getFileAsStream(inputPath),
                software, species, gene);

        var output = CommonUtils.createFileAsStream(outputPath);
        try (var pw = new PrintWriter(output)) {
            pw.println("from.id\tto.id\tfrom.cdr3nt.aln\tto.cdr3nt.aln\tsubsts\tindels");
            new Cdr3NtScopedGraph<>(input, kNn, maxSubstitutions, maxIndels)
                    .parallelStream()
                    .forEach(edge -> {
                        var helper = edge.getAlignment().getAlignmentHelper();
                        int totalMut = edge.getAlignment().getAbsoluteMutations().size(),
                                indels = edge.getAlignment().getAbsoluteMutations().countOfIndels();
                        pw.println(edge.getFrom().getId() + "\t" +
                                edge.getTo().getId() + "\t" +
                                helper.getSeq1String() + "\t" +
                                helper.getSeq2String() + "\t" +
                                (totalMut - indels) + "\t" +
                                indels
                        );
                    });
        }

        return null;
    }
}
