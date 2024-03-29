package com.antigenomics.mir.scripts;

import com.antigenomics.mir.MultiTableWriter;
import com.antigenomics.mir.structure.output.GeneralAnnotationWriter;
import com.antigenomics.mir.structure.output.MarkupAnnotationWriter;
import com.antigenomics.mir.structure.output.TcrPeptideMhcContactMapWriter;
import com.antigenomics.mir.structure.pdb.parser.PdbParserUtils;
import com.antigenomics.mir.structure.TcrPeptideMhcComplex;
import com.antigenomics.mir.structure.mapper.PeptideMhcComplexMapper;
import com.antigenomics.mir.structure.output.*;
import com.antigenomics.mir.structure.pdb.contacts.TcrPeptideMhcContactMap;
import picocli.CommandLine;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@CommandLine.Command(name = "compute-tpm-contacts",
        sortOptions = false,
        description = "Computes residue-residue contacts provided a set of TCR:pMHC complexes. " +
                "Can also permute pMHCs across structures to generate mock complexes " +
                "Will generate the following annotation file:" +
                "[general] annotation of PDB structure chains, " +
                "specifying TCR/peptide/MHC chains and corresponding alleles; " +
                "[markup] annotates PDB chain sequences specifying various structural regions, " +
                "e.g. FR/CDRs and MHC regions; " +
                "[contacts] Minimal and CA atom distances between residues closer than a specified threshold " +
                "(will output all contacts for CDR1-3:peptide); " +
                "[CAUTION] 1) setting too loose distance thresholds will result in extremely large output files;" +
                "2) Structures should be aligned by their TCRab in order for permutations to work.",
        mixinStandardHelpOptions = true)
public class ComputeTPMContacts extends IOPathBaseScript {
    @CommandLine.Option(names = {"-t1", "--max-ca-dist"},
            paramLabel = "<float>",
            defaultValue = "25.0",
            description = "Maximal CA distance between residues (default: ${DEFAULT-VALUE})")
    private float maxCaDist;

    @CommandLine.Option(names = {"-t2", "--max-atom-dist"},
            paramLabel = "<float>",
            defaultValue = "5.0",
            description = "Maximal distance between atoms (default: ${DEFAULT-VALUE})")
    private float maxAtomDist;

    @CommandLine.Option(names = {"-p", "--permute"},
            description = "If set, will permute pMHCs replacing native pMHCs for TCRs producing " +
                    "all possible TCR:pMHC combinations")
    private boolean performPermutations;

    @CommandLine.Option(names = {"-s", "--permute-species"},
            description = "If set, allows permutations across species")
    private boolean permuteCrossSpecies;

    @Override
    public Void call() throws Exception {
        // Perform structure mapping
        // structure -> annotated structure mapper
        var mapper = PeptideMhcComplexMapper.DEFAULT;

        var mappedComplexes = inputPaths.parallelStream().map(
                inputFileName -> {
                    var inputFile = new File(inputFileName);
                    var mappingResult = Optional.<TcrPeptideMhcComplex>empty();
                    try {
                        // read PDB
                        var structFile = PdbParserUtils.parseStructure(inputFile.getName(),
                                new FileInputStream(inputFile));

                        // annotate geometry
                        mappingResult = mapper.map(structFile);
                    } catch (Exception e) {
                        System.err.println("Failed to load " + inputFile.getName() + ":\n" +
                                e.toString());
                        e.printStackTrace();
                    }

                    if (mappingResult.isPresent()) {
                        System.err.println("Mapped " + inputFile.getName());
                    } else {
                        System.err.println("Failed to map " + inputFile);
                    }

                    return mappingResult;
                }
        ).flatMap(Optional::stream).collect(Collectors.toList());

        try (var annotationWriter = new MultiTableWriter<>(Arrays.asList(
                new GeneralAnnotationWriter(getOutputStream("general.txt")),
                new MarkupAnnotationWriter(getOutputStream("markup.txt"))));
             var contactWriter = new TcrPeptideMhcContactMapWriter(getOutputStream("contacts.txt"))
        ) {
            mappedComplexes.parallelStream().forEach(
                    mappedComplex -> {
                        annotationWriter.accept(mappedComplex);

                        var otherMappedComplexes = performPermutations ?
                                mappedComplexes :
                                Collections.singletonList(mappedComplex);

                        otherMappedComplexes.parallelStream().forEach(
                                otherMappedComplex -> {
                                    if (permuteCrossSpecies ||
                                            mappedComplex.getSpecies() == otherMappedComplex.getSpecies()) {
                                        var contactMap = new TcrPeptideMhcContactMap(mappedComplex, otherMappedComplex);
                                        contactWriter.accept(contactMap);
                                    }
                                }
                        );

                        System.err.println("Processed contacts for " + mappedComplex.getStructure().getId());
                    }
            );
        }

        return null;
    }
}
