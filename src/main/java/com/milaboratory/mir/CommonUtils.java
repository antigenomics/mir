package com.milaboratory.mir;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class CommonUtils {
    private static final CommonUtils THIS = new CommonUtils();

    private CommonUtils() {
    }

    public static InputStream getFileAsStream(String path) throws IOException {
        return getFileAsStream(path, isGzipped(path));
    }

    public static InputStream getFileAsStream(String path, boolean gzip) throws IOException {
        var stream = new FileInputStream(path);
        return gzip ? new GZIPInputStream(stream) : stream;
    }

    public static InputStream getResourceAsStream(String path) throws IOException {
        return getResourceAsStream(path, isGzipped(path));
    }

    public static InputStream getResourceAsStream(String path, boolean gzip) throws IOException {
        var stream = THIS.getClass().getClassLoader().getResourceAsStream(path);
        return gzip ? new GZIPInputStream(stream) : stream;
    }

    private static boolean isGzipped(String path) {
        return path.toLowerCase().endsWith(".gz");
    }
}
