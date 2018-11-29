package com.milaboratory.mir.scripts;

import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import com.milaboratory.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.milaboratory.mir.mappers.markup.SequenceRegion;
import com.milaboratory.mir.mappers.markup.SequenceRegionMarkup;
import com.milaboratory.mir.structure.AntigenReceptor;
import com.milaboratory.mir.structure.MhcComplex;
import com.milaboratory.mir.structure.mapper.PeptideMhcComplexMapper;
import com.milaboratory.mir.structure.pdb.Chain;
import com.milaboratory.mir.structure.pdb.parser.PdbParserUtils;
import picocli.CommandLine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@CommandLine.Command(name = "filter-structures",
        sortOptions = false,
        description = "Filters PDB structures and retains the parts that were annotated as TCR:pMHC. ",
        mixinStandardHelpOptions = true)
public class FilterStructures implements Callable<Void> {
    @CommandLine.Option(names = {"-I", "--input"},
            required = true,
            paramLabel = "<path/to/pdb>",
            arity = "1..*",
            description = "Path to input file(s), space-separated")
    private List<String> inputPaths;

    @CommandLine.Option(names = {"-O", "--output"},
            required = true,
            paramLabel = "<path/prefix>",
            description = "Path and prefix for output files")
    private String outputPrefix;

    @Override
    public Void call() throws Exception {
        // create all missing folders in output path
        var targetPath = new File(createPath("tmp"));
        var parent = targetPath.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }

        // structure -> annotated structure mapper
        var mapper = new PeptideMhcComplexMapper(
                // todo: rewrite simple mapper to kmer based
                new SimpleExhaustiveMapperFactory<>(
                        // todo: can set up as parameter
                        AffineGapAlignmentScoring.getAminoAcidBLASTScoring(BLASTMatrix.BLOSUM62))
        );

        inputPaths.parallelStream().forEach(
                inputFileName -> {
                    var inputFile = new File(inputFileName);
                    try (PrintWriter pw = new PrintWriter(outputPrefix + "/" + inputFileName)) {
                        // read PDB
                        var structFile = PdbParserUtils.parseStructure(inputFile.getName(),
                                new FileInputStream(inputFile));

                        // get mapping result
                        var resOpt = mapper.map(structFile);
                        if (resOpt.isPresent()) {
                            PdbParserUtils.writeStructure(resOpt.get().getRegions(), pw);
                            System.err.println("Processed " + inputFile.getName());
                        } else {
                            System.err.println("Failed to map " + inputFile);
                        }
                    } catch (Exception e) {
                        System.err.println("Failed to process " + inputFile.getName() + ":\n" +
                                e.toString());
                        e.printStackTrace();
                    }
                }
        );

        return null;
    }

    private String createPath(String suffix) {
        return outputPrefix +
                (outputPrefix.endsWith(File.separator) ? "" : ".") +
                suffix + ".txt";
    }
}
