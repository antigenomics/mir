package com.milaboratory.mir.scripts;

import com.milaboratory.core.mutations.Mutations;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.clonotype.parser.ClonotypeTableParserUtils;
import com.milaboratory.mir.clonotype.parser.Software;
import com.milaboratory.mir.clonotype.rearrangement.ClonotypeWithRearrangementInfo;
import com.milaboratory.mir.clonotype.rearrangement.JunctionMarkup;
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

    @CommandLine.Option(names = {"-U", "--max-aa-substs"},
            paramLabel = "<int>",
            defaultValue = "-1",
            description = "Maximal number of AA substitutions allowed, if set to (default: ${DEFAULT-VALUE}) will be recomputed from NT substitutions")
    private int maxAaSubstitutions;

    @CommandLine.Option(names = {"-D", "--max-aa-indels"},
            paramLabel = "<int>",
            defaultValue = "-1",
            description = "Maximal number of AA indels allowed, if set to (default: ${DEFAULT-VALUE}) will be recomputed from NT substitutions")
    private int maxAaIndels;

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
            pw.println("from.id\tto.id\tfrom.cdr3nt.aln\tto.cdr3nt.aln\tsubsts\tindels\t" +
                    "from.v.end\tfrom.d.start\tfrom.d.end\tfrom.j.start\t" +
                    "to.v.end\tto.d.start\tto.d.end\tto.j.start");
            new Cdr3NtScopedGraph<>(input, kNn,
                    maxSubstitutions, maxIndels,
                    maxAaSubstitutions, maxAaIndels)
                    .parallelStream()
                    .forEach(edge -> {
                        var helper = edge.getAlignment().getAlignmentHelper();
                        int totalMut = edge.getAlignment().getAbsoluteMutations().size(),
                                indels = edge.getAlignment().getAbsoluteMutations().countOfIndels();

                        String seq1String = helper.getSeq1String();
                        String seq2String = helper.getSeq2String();

                        pw.println(edge.getFrom().getId() + "\t" +
                                edge.getTo().getId() + "\t" +
                                seq1String + "\t" +
                                seq2String + "\t" +
                                (totalMut - indels) + "\t" +
                                indels + "\t" +
                                shiftMarkup(edge.getFrom().getClonotype(), seq1String).asRow() + "\t" +
                                shiftMarkup(edge.getTo().getClonotype(), seq2String).asRow()
                        );
                    });
        }

        return null;
    }

    private static <T extends Clonotype> JunctionMarkup shiftMarkup(T clonotype,
                                                                    String seq) {
        // quick and dirty way to get new coords
        JunctionMarkup junctionMarkup = clonotype instanceof ClonotypeWithRearrangementInfo ?
                ((ClonotypeWithRearrangementInfo) clonotype).getJunctionMarkup() :
                JunctionMarkup.DUMMY;

        int vEnd = junctionMarkup.getVEnd(),
                dStart = junctionMarkup.getDStart(),
                dEnd = junctionMarkup.getDEnd(),
                jStart = junctionMarkup.getJStart();

        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) == '-') {
                if (vEnd > i) {
                    vEnd++;
                }
                if (dStart >= i) {
                    dStart++;
                }
                if (dEnd > i) {
                    dEnd++;
                }
                if (jStart >= i) {
                    jStart++;
                }
            }
        }

        return new JunctionMarkup(vEnd, jStart, dStart, dEnd);
    }
}
