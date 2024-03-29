package com.antigenomics.mir.scripts;

import picocli.CommandLine;

@CommandLine.Command(name = "examples",
        description = "Runs one of the examples, see the list below",
        mixinStandardHelpOptions = true)
public class Examples implements Runnable {
    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new Examples())
                .addSubcommand("cdr3nt-graph", new ComputeCdr3NtScopedGraph())
                .addSubcommand("annotate-structures", new AnnotateStructures())
                .addSubcommand("compute-pdb-geom", new ComputePDBGeometry())
                .addSubcommand("compute-pdb-contacts", new ComputePDBContacts())
                .addSubcommand("filter-structures", new FilterStructures())
                .addSubcommand("clonotype-summary-stats", new ClonotypeSummaryStatistics())
                .addSubcommand("compute-tpm-contacts", new ComputeTPMContacts())
                .addSubcommand("cdr3aavj-pairwise-dist", new ComputeCdr3VJPairwiseDistances())
                .addSubcommand("compute-receptor-contigs", new ComputeReceptorContigs());
        cmd.parseWithHandler(new CommandLine.RunAll(), args);
    }

    @Override
    public void run() {
    }
}
