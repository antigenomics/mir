package com.milaboratory.mir.scripts;

import picocli.CommandLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class IOPathBaseScript implements Callable<Void> {
    @CommandLine.Option(names = {"-I", "--input"},
            required = true,
            paramLabel = "<path/to/files>",
            arity = "1..*",
            description = "Path to input file(s), space-separated")
    protected List<String> inputPaths;

    @CommandLine.Option(names = {"-O", "--output"},
            required = true,
            paramLabel = "<path/prefix>",
            description = "Path and prefix for output files")
    protected String outputPrefix;

    private void makeFolders() {
        var targetPath = new File(createPath("tmp", false));
        var parent = targetPath.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
    }

    protected String createPath(String suffix, boolean safe) {
        if (safe) {
            makeFolders();
        }

        return outputPrefix +
                (outputPrefix.endsWith(File.separator) ? "" : ".") +
                suffix;
    }

    protected OutputStream getOutputStream(String suffix) throws FileNotFoundException {
        return new FileOutputStream(createPath(suffix, true));
    }
}
