package com.antigenomics.mir.scripts;

import com.antigenomics.mir.MultiTableWriter;
import com.antigenomics.mir.structure.output.AtomDistanceWriter;
import com.antigenomics.mir.structure.output.CaDistanceWriter;
import com.antigenomics.mir.structure.pdb.parser.PdbParserUtils;
import com.antigenomics.mir.structure.output.*;
import com.antigenomics.mir.structure.pdb.contacts.StructurePairwiseDistances;
import picocli.CommandLine;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "compute-pdb-contacts",
        sortOptions = false,
        description = "Computes residue-residue contacts provided a file in PDB format. " +
                "Will output two types of annotations:" +
                "[cadist] CA atom distances between residues closer than a specified threshold; " +
                "[atomdist] pairwise distances between residue atoms in case they are close enough; " +
                "!IMPORTANT! setting too loose distance thresholds will result in extremely large output files",
        mixinStandardHelpOptions = true)
public class ComputePDBContacts implements Callable<Void> {
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

    @CommandLine.Option(names = {"-s", "--allow-self"},
            description = "If set, will also compute distances between residues located in the same chain")
    private boolean allowSelf;

    // todo: specify other parameters

    @Override
    public Void call() throws Exception {
        // create all missing folders in output path
        var targetPath = new File(createPath("tmp"));
        var parent = targetPath.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }

        try (var writer = new MultiTableWriter<>(Arrays.asList(
                new CaDistanceWriter(getOutputStream("cadist")),
                new AtomDistanceWriter(getOutputStream("atomdist")))
        )) {
            inputPaths.parallelStream().forEach(
                    inputFileName -> {
                        var inputFile = new File(inputFileName);

                        try {
                            // read PDB
                            var structFile = PdbParserUtils.parseStructure(inputFile.getName(),
                                    new FileInputStream(inputFile));

                            // compute geometry
                            var dists = new StructurePairwiseDistances(structFile,
                                    allowSelf, maxCaDist, maxAtomDist);

                            writer.accept(dists);
                            System.err.println("Processed " + inputFile.getName());
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
