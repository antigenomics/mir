package com.milaboratory.mir.scripts;

import com.milaboratory.mir.summary.ClonotypeSummaryTableHelper;
import com.milaboratory.mir.summary.ClonotypeSummaryType;
import picocli.CommandLine;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

@CommandLine.Command(name = "clonotype-summary-stats",
        sortOptions = false,
        description = "Computes specified summary statistics for a " +
                "set of clonotype tables. ",
        mixinStandardHelpOptions = true)
public class ClonotypeSummaryStatistics extends ClonotypeTableBaseScript {
    @CommandLine.Option(names = {"-w", "--weighted"},
            description = "If set, will weight each clonotype according to its frequency")
    private boolean weightByFrequency;

    @CommandLine.Option(names = {"-U", "--summarize"},
            required = true,
            paramLabel = "<id>",
            arity = "1..*",
            description = "Types of summaries to compute (allowed values: comma-separated list of ${COMPLETION-CANDIDATES})")
    protected ClonotypeSummaryType[] summaryTypes;

    @Override
    public Void call() {
        var writers = Arrays.stream(summaryTypes).collect(Collectors.toMap(
                summaryType -> summaryType,
                summaryType -> {
                    try {
                        var res = new PrintWriter(getOutputStream(summaryType.toString().toLowerCase()));
                        res.println("sample.id\t" + ClonotypeSummaryTableHelper.getHeader(summaryType));
                        return res;
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
        ));

        getClonotypePipesWithId()
                .forEach((sampleId, clonotypeCallPipe) -> {
                    System.out.println("Processing " + sampleId);
                    var tableMap = Arrays.stream(summaryTypes)
                            .collect(Collectors.toMap(
                                    summaryType -> summaryType,
                                    summaryType -> ClonotypeSummaryTableHelper.create(summaryType, weightByFrequency)
                            ));

                    clonotypeCallPipe
                            .parallelStream()
                            .forEach(clonotypeCall -> tableMap.values().forEach(table -> table.accept(clonotypeCall)));

                    tableMap
                            .forEach((summaryType, table) -> {
                                var writer = writers.get(summaryType);
                                ClonotypeSummaryTableHelper
                                        .countersAsRows(table.getCounters())
                                        .forEach(row -> writer.println(sampleId + "\t" + row));
                            });
                });

        writers.values().forEach(PrintWriter::close);

        return null;
    }
}
