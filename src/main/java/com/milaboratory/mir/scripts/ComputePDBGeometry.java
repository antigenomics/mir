package com.milaboratory.mir.scripts;

import com.milaboratory.mir.MultiTableWriter;
import com.milaboratory.mir.structure.output.*;
import com.milaboratory.mir.structure.pdb.geometry.StructureAxesAndTorsions;
import com.milaboratory.mir.structure.pdb.parser.PdbParserUtils;
import picocli.CommandLine;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "compute-pdb-geom",
        sortOptions = false,
        description = "Computes rigid body properties for a list of structure files in PDB format. " +
                "Will output three types of annotations:" +
                "[praxis] center of mass and principal axes; " +
                "[cacoord] coordinates of CA atoms (backbone) for each residue; " +
                "[torsions] three torsion angles for each residue",
        mixinStandardHelpOptions = true)
public class ComputePDBGeometry implements Callable<Void> {
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

    // todo: specify other parameters

    @Override
    public Void call() throws Exception {
        // createBins all missing folders in output path
        var targetPath = new File(createPath("tmp"));
        var parent = targetPath.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't createBins dir: " + parent);
        }

        try (var writer = new MultiTableWriter<>(Arrays.asList(
                new PrincipalAxesWriter(getOutputStream("praxis")),
                new BackboneWriter(getOutputStream("cacoord")),
                new TorsionAngleWriter(getOutputStream("torsions")))
        )) {
            inputPaths.parallelStream().forEach(
                    inputFileName -> {
                        var inputFile = new File(inputFileName);

                        try {
                            // read PDB
                            var structFile = PdbParserUtils.parseStructure(inputFile.getName(),
                                    new FileInputStream(inputFile));

                            // compute geometry
                            var geom = new StructureAxesAndTorsions(structFile);

                            writer.accept(geom);
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
