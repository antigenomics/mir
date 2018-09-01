package com.milaboratory.mir;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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

    public static <K1, V1, K2, V2> Map<K2, V2> map2map(Map<K1, V1> map,
                                                       Function<Map.Entry<K1, V1>, K2> keyMapper,
                                                       Function<Map.Entry<K1, V1>, V2> valueMapper) {
        return map.entrySet().stream().collect(Collectors.toMap(keyMapper, valueMapper));
    }
}
