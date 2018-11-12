package com.milaboratory.mir.scripts;

import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import com.milaboratory.mir.MultiTableWriter;
import com.milaboratory.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.milaboratory.mir.structure.mapper.PeptideMhcComplexMapper;
import com.milaboratory.mir.structure.output.*;
import com.milaboratory.mir.structure.pdb.parser.PdbParserUtils;
import picocli.CommandLine;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "annotate-structures",
        sortOptions = false,
        description = "Annotates a list of structure files in PDB format. " +
                "Will output tree levels of annotations:" +
                "[general] annotation of PDB structure chains, " +
                "specifying TCR/peptide/MHC chains and corresponding alleles; " +
                "[markup] annotates PDB chain sequences specifying various structural regions, " +
                "e.g. FR/CDRs and MHC regions; " +
                "[resmarkup] annotates regions and output a detailed annotation for each PDB residue record. ",
        mixinStandardHelpOptions = true)
public class AnnotateStructures implements Callable<Void> {
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

    // todo: specify libraries, annotations to use

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

        try (var writer = new MultiTableWriter<>(Arrays.asList(
                new GeneralAnnotationWriter(getOutputStream("general")),
                new MarkupAnnotationWriter(getOutputStream("markup")),
                new ResidueMarkupAnnotationWriter(getOutputStream("resmarkup"))
        ))) {
            inputPaths.parallelStream().forEach(
                    inputFileName -> {
                        var inputFile = new File(inputFileName);

                        try {
                            // read PDB
                            var structFile = PdbParserUtils.parseStructure(inputFile.getName(),
                                    new FileInputStream(inputFile));

                            // get mapping result
                            var resOpt = mapper.map(structFile);
                            if (resOpt.isPresent()) {
                                writer.accept(resOpt.get());
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
        }

        return null;
    }

    private String createPath(String suffix) {
        return outputPrefix +
                (outputPrefix.endsWith(File.separator) ? "" : ".") +
                suffix + ".txt";
    }

    private OutputStream getOutputStream(String suffix) throws FileNotFoundException {
        return new FileOutputStream(createPath(suffix));
    }
}
