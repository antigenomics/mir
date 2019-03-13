package com.antigenomics.mir.scripts;

import com.antigenomics.mir.mappers.align.SimpleExhaustiveMapperFactory;
import com.antigenomics.mir.structure.pdb.parser.PdbParserUtils;
import com.milaboratory.core.alignment.AffineGapAlignmentScoring;
import com.milaboratory.core.alignment.BLASTMatrix;
import com.antigenomics.mir.structure.mapper.PeptideMhcComplexMapper;
import picocli.CommandLine;

import java.io.*;

@CommandLine.Command(name = "filter-structures",
        sortOptions = false,
        description = "Filters PDB structures and retains the parts that were annotated as TCR:pMHC. ",
        mixinStandardHelpOptions = true)
public class FilterStructures extends IOPathBaseScript {
    @Override
    public Void call() throws Exception {
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
                    try (PrintWriter pw = new PrintWriter(getOutputStream(inputFileName))) {
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
}
