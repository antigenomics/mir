package com.milaboratory.mir.scripts;

import com.milaboratory.mir.CommonUtils;
import com.milaboratory.mir.clonotype.Clonotype;
import com.milaboratory.mir.clonotype.ClonotypeCall;
import com.milaboratory.mir.clonotype.io.ClonotypeTablePipe;
import com.milaboratory.mir.clonotype.parser.ClonotypeTableParserUtils;
import com.milaboratory.mir.clonotype.parser.Software;
import com.milaboratory.mir.segment.Gene;
import com.milaboratory.mir.segment.Species;
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
