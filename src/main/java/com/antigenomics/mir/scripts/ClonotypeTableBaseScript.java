package com.antigenomics.mir.scripts;

import com.antigenomics.mir.CommonUtils;
import com.antigenomics.mir.clonotype.Clonotype;
import com.antigenomics.mir.clonotype.io.ClonotypeTablePipe;
import com.antigenomics.mir.clonotype.parser.ClonotypeTableParserUtils;
import com.antigenomics.mir.clonotype.parser.Software;
import com.antigenomics.mir.segment.Gene;
import com.antigenomics.mir.Species;
import picocli.CommandLine;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ClonotypeTableBaseScript extends IOPathBaseScript {
    @CommandLine.Option(names = {"-F", "--software"},
            required = true,
            paramLabel = "<id>",
            description = "Software format of input file (allowed values: ${COMPLETION-CANDIDATES})")
    protected Software software;

    @CommandLine.Option(names = {"-S", "--species"},
            required = true,
            paramLabel = "<id>",
            description = "Specify organism (allowed values: ${COMPLETION-CANDIDATES})")
    protected Species species;

    @CommandLine.Option(names = {"-G", "--gene"},
            required = true,
            paramLabel = "<id>",
            description = "Specify antigen receptor gene (allowed values: ${COMPLETION-CANDIDATES})")
    protected Gene gene;

    // todo: custom segment library?

    protected Collection<ClonotypeTablePipe<? extends Clonotype>> getClonotypePipes() {
        return getClonotypePipesWithId().values();
    }

    protected Map<String, ClonotypeTablePipe<? extends Clonotype>> getClonotypePipesWithId() {
        return inputPaths.stream().collect(Collectors.toMap(
                inputPath -> inputPath,
                inputPath ->
                {
                    try {
                        return ClonotypeTableParserUtils.streamFrom(
                                CommonUtils.getFileAsStream(inputPath),
                                software, species, gene);
                    } catch (IOException e) {
                        throw new RuntimeException("Error reading " + inputPath, e);
                    }
                }
        ));
    }
}
